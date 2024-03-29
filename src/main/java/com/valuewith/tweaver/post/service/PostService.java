package com.valuewith.tweaver.post.service;

import static com.valuewith.tweaver.constants.ErrorCode.POST_NOT_FOUND_FOR_DELETE;
import static com.valuewith.tweaver.constants.ErrorCode.POST_NOT_FOUND_FOR_UPDATE;
import static com.valuewith.tweaver.constants.ErrorCode.POST_WRITER_NOT_MATCH;

import com.valuewith.tweaver.exception.CustomException;
import com.valuewith.tweaver.group.entity.TripGroup;
import com.valuewith.tweaver.group.service.TripGroupService;
import com.valuewith.tweaver.member.entity.Member;
import com.valuewith.tweaver.member.service.MemberService;
import com.valuewith.tweaver.post.dto.PostForm;
import com.valuewith.tweaver.post.dto.PostListResponseDto;
import com.valuewith.tweaver.post.dto.PostResponseDto;
import com.valuewith.tweaver.post.dto.PostUpdateForm;
import com.valuewith.tweaver.post.entity.Post;
import com.valuewith.tweaver.post.repository.PostRepository;
import com.valuewith.tweaver.postImage.service.PostImageService;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final MemberService memberService;
  private final TripGroupService tripGroupService;
  private final PostImageService postImageService;

  @Transactional
  public void createPost(Long memberId, PostForm postForm,
      List<MultipartFile> images) {

    Member postCreator = memberService.findMemberByMemberId(memberId);
    TripGroup trip = tripGroupService.findTripByTripGroupId(postForm.getTripGroupId());

    Post post = Post.builder()
        .title(postForm.getTitle())
        .content(postForm.getContent())
        .member(postCreator)
        .tripGroup(trip)
        .build();

    if (images != null && !images.isEmpty()) {  // 이미지가 있으면 이미지 리스트 생성
      postImageService.saveImageList(images, post);
    }

    postRepository.save(post);
  }

  public PostListResponseDto getFilteredPostList(String area, String title, Pageable pageable) {

    List<Post> postList = postRepository.findPostByTitleAndTripGroupArea(title, area, pageable);
    List<PostResponseDto> postResponses = postList.stream()
        .map(PostResponseDto::from)
        .collect(Collectors.toList());

    long total = postList.size();
    Integer totalPages = (int) Math.ceil((double) total / pageable.getPageSize());
    Boolean isLast = pageable.getOffset() + pageable.getPageSize() >= total;

    return PostListResponseDto.from(postResponses, pageable.getPageNumber() + 1,
        totalPages, total, isLast);
  }

  @Transactional
  public void updatePost(PostUpdateForm postUpdateForm, Long memberId, Long postId) {
    Post postForUpdate = postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(POST_NOT_FOUND_FOR_UPDATE));

    validPostMember(postForUpdate, memberId);  // 401 에러

    postForUpdate.updateFrom(postUpdateForm);
  }

  @Transactional
  public void deletePost(Long memberId, Long postId) {
    log.debug("포스트 삭제 중");
    Post postForDelete = findPostForDelete(postId);

    validPostMember(postForDelete, memberId);
    log.debug("포스트 삭제 검증 완료");

    postRepository.delete(postForDelete);
    postImageService.deleteImageList(postForDelete);
  }

  private void validPostMember(Post post, Long memberId) {
    if (!Objects.equals(post.getMember().getMemberId(), memberId)) {
      throw new CustomException(POST_WRITER_NOT_MATCH);  // 401
    }
  }

  private Post findPostForDelete(Long postId) {
    return postRepository.findById(postId)
        .orElseThrow(() -> new CustomException(POST_NOT_FOUND_FOR_DELETE));  // 404
  }
}
