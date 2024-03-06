package com.valuewith.tweaver.calendar.dto;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalendarDetailResponseDto {
  private Long postId;
  private String postTile;
  private String postContent;
}
