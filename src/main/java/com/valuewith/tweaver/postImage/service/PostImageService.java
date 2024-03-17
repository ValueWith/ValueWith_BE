package com.valuewith.tweaver.postImage.service;

import static com.valuewith.tweaver.constants.ErrorCode.FAILURE_DELETE_IMAGE;
import static com.valuewith.tweaver.constants.ErrorCode.IMAGE_SAVE_ERROR;
import static com.valuewith.tweaver.constants.ErrorCode.POST_NOT_FOUND_FOR_DELETE;
import static com.valuewith.tweaver.constants.ErrorCode.POST_WRITER_NOT_MATCH;

import com.valuewith.tweaver.constants.ImageType;
import com.valuewith.tweaver.defaultImage.service.ImageService;
import com.valuewith.tweaver.exception.CustomException;
import com.valuewith.tweaver.member.service.MemberService;
import com.valuewith.tweaver.post.entity.Post;
import com.valuewith.tweaver.post.repository.PostRepository;
import com.valuewith.tweaver.postImage.dto.PostImageDto;
import com.valuewith.tweaver.postImage.entity.PostImage;
import com.valuewith.tweaver.postImage.repository.PostImageRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostImageService {

  private final ImageService imageService;
  private final PostImageRepository postImageRepository;
  private final PostRepository postRepository;

  public void saveImageList(List<MultipartFile> images, Post post) {
    try {
      log.info("포스트 이미지 저장중 - " + post.getTitle());
      List<PostImage> imageList = new ArrayList<>();

      for(MultipartFile file : images) {
        PostImageDto postImageDto = new PostImageDto();
        String currentUrl = imageService.uploadImageAndGetUrl(file, ImageType.POST);
        log.debug("이미지 url: " + currentUrl);

        PostImageDto imageDto = postImageDto.setPostImageUrlAndPost(currentUrl, post);
        imageList.add(PostImage.from(imageDto));
      }
      // 저장
      postImageRepository.saveAll(imageList);
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new CustomException(IMAGE_SAVE_ERROR);
    }
  }

  @Transactional
  public void deleteImageList(Post postForDelete) {
    try {
      log.info("포스트 이미지 삭제중 - " + postForDelete.getTitle());
      List<PostImage> postImageList = postForDelete.getPostImages();

      if (postImageList.isEmpty()) {
        log.info("포스트 이미지 없음");
        postImageRepository.deleteAll(postImageList);
        log.info("포스트 이미지 삭제 완료 - " + postForDelete.getTitle());
        return;
      }
      // 저장된 사진 전부 삭제
      for (PostImage img : postImageList) {
        imageService.deleteImageFile(img.getPostImageUrl());
      }

      // 사진 객체 모두 삭제
      postImageRepository.deleteAll(postImageList);
      log.info("포스트 이미지 삭제 완료 - " + postForDelete.getTitle());
    } catch (Exception e) {
      log.error(e.getMessage());
      throw new CustomException(FAILURE_DELETE_IMAGE);
    }
  }

  private void validPostMember(Post post, Long memberId) {
    if (!Objects.equals(post.getMember().getMemberId(), memberId)) {
      throw new CustomException(POST_WRITER_NOT_MATCH);
    }
  }
}
