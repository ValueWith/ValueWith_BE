package com.valuewith.tweaver.bookmark.controller;

import com.valuewith.tweaver.bookmark.service.BookmarkService;
import com.valuewith.tweaver.commons.security.service.TokenService;
import com.valuewith.tweaver.config.SwaggerConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {SwaggerConfig.BOOKMARK_TAG})
@RestController
@RequestMapping("/bookmark")
@RequiredArgsConstructor
public class BookmarkController {
  private final TokenService tokenService;
  private final BookmarkService bookmarkService;

  @ApiOperation(
      value = "북마크 생성",
      notes = "관심 있는 그룹에 북마크 생성할 때 사용"
  )
  @PostMapping("/{tripGroupId}")
  public ResponseEntity<String> createBookmark(
      @RequestHeader("Authorization") String token,
      @PathVariable Long tripGroupId) {
    bookmarkService.createBookmark(tokenService.getMemberId(token), tripGroupId);
    return ResponseEntity.ok("ok");
  }

  @ApiOperation(
      value = "북마크 삭제",
      notes = "그룹에 북마크 삭제할 때 사용."
  )
  @DeleteMapping("/{bookmarkId}")
  public ResponseEntity<String> removeChatRoom(
      @RequestHeader("Authorization") String token,
      @PathVariable Long bookmarkId
  ) {
    bookmarkService.removeBookmark(tokenService.getMemberId(token), bookmarkId);
    return ResponseEntity.ok("ok");
  }
}
