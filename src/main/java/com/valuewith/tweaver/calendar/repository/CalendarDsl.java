package com.valuewith.tweaver.calendar.repository;

import com.valuewith.tweaver.calendar.dto.CalendarDetailResponseDto;
import com.valuewith.tweaver.calendar.dto.CalendarResponseDto;
import java.time.LocalDate;
import java.util.List;

public interface CalendarDsl {
  List<CalendarResponseDto> getCalenderListFromTripGroup(Long memberId, LocalDate date, String type);
  List<CalendarResponseDto> getCalenderListFromGroupMember(Long memberId, LocalDate date, String type);
  List<CalendarDetailResponseDto> getCalendarDetail(Long memberId, LocalDate date, Long tripGroupId);
}
