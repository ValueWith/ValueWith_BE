package com.valuewith.tweaver.post.controller;

import com.valuewith.tweaver.commons.PrincipalDetails;
import com.valuewith.tweaver.post.dto.PostForm;
import com.valuewith.tweaver.post.service.PostService;
import java.util.List;
import javax.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
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
  public ResponseEntity<String> createPost(
      @AuthenticationPrincipal PrincipalDetails principalDetails,
      @RequestPart @Valid PostForm postForm,
      @RequestPart(required = false) List<MultipartFile> images) {

    return ResponseEntity.ok(postService.createPost(principalDetails, postForm, images));
  }
}
