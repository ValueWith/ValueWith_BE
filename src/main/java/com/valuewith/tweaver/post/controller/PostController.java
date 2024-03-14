package com.valuewith.tweaver.post.controller;

import com.valuewith.tweaver.auth.CustomAuthPrincipal;
import com.valuewith.tweaver.commons.PrincipalDetails;
import com.valuewith.tweaver.post.dto.PostForm;
import com.valuewith.tweaver.post.dto.PostListResponseDto;
import com.valuewith.tweaver.post.dto.PostUpdateForm;
import com.valuewith.tweaver.post.service.PostService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController {

  private final PostService postService;

  @PostMapping
  public ResponseEntity<Void> createPost(
      @CustomAuthPrincipal PrincipalDetails principalDetails,
      @RequestPart @Valid PostForm postForm,
      @RequestPart(required = false) List<MultipartFile> images) {
    postService.createPost(principalDetails, postForm, images);

    return ResponseEntity.status(HttpStatus.CREATED).build();  // 201
  }

  @GetMapping("/list")
  public ResponseEntity<PostListResponseDto> getPostList(
      @RequestParam(defaultValue = "1") Integer page,
      @RequestParam(defaultValue = "all") String area,
      @RequestParam(defaultValue = "") String title
  ) {
    Pageable pageable = PageRequest.of(page - 1, 12);
    return ResponseEntity.ok(postService.getFilteredPostList(area, title, pageable));
  }

  @PatchMapping("/{postId}")
  public ResponseEntity<Void> updatePost(
      @CustomAuthPrincipal PrincipalDetails principalDetails,
      @PathVariable Long postId,
      @RequestBody @Valid PostUpdateForm postUpdateForm
  ) {

    Long memberId = principalDetails.getId();  // [001] 401 unauthorized
    postService.updatePostList(postUpdateForm, memberId, postId);

    return ResponseEntity.noContent().build();  // 204
  }
}
