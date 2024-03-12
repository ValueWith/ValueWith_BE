package com.valuewith.tweaver.post.dto;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class PostListResponseDto {

  private Integer currentPage;
  private Integer totalPages;
  private Long totalElements;
  private boolean last;
  private List<PostResponseDto> postList;

  public static PostListResponseDto from(List<PostResponseDto> postList, Integer pageNumber,
      Integer totalPages, Long totalElements, Boolean last) {
    return PostListResponseDto.builder()
        .postList(postList)
        .currentPage(pageNumber)
        .totalPages(totalPages)
        .totalElements(totalElements)
        .last(last)
        .build();
  }
}
