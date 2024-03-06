package com.valuewith.tweaver.post.service;

import com.valuewith.tweaver.commons.PrincipalDetails;
import com.valuewith.tweaver.group.entity.TripGroup;
import com.valuewith.tweaver.group.service.TripGroupService;
import com.valuewith.tweaver.member.entity.Member;
import com.valuewith.tweaver.member.service.MemberService;
import com.valuewith.tweaver.post.dto.PostForm;
import com.valuewith.tweaver.post.entity.Post;
import com.valuewith.tweaver.post.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostService {

  private final PostRepository postRepository;
  private final MemberService memberService;
  private final TripGroupService tripGroupService;

  @Transactional
  public String createPost(PrincipalDetails principalDetails, PostForm postForm) {

    Member postCreator = memberService.findMemberByEmail(principalDetails.getUsername());
    TripGroup trip = tripGroupService.findTripByTripGroupId(postForm.getTripGroupId());

    Post post = Post.builder()
        .title(postForm.getTitle())
        .content(postForm.getContent())
        .member(postCreator)
        .tripGroup(trip)
        .build();

    postRepository.save(post);

    return "ok";
  }
}
