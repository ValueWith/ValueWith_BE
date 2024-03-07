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
public class CalendarResponseDto {
  private Long tripGroupId;
  private LocalDate tripDate;
  private String tripGroupName;
}
