package com.valuewith.tweaver.calendar.repository;

import com.valuewith.tweaver.calendar.dto.CalendarResponseDto;
import java.time.LocalDate;
import java.util.List;

public interface CalendarDsl {
  List<CalendarResponseDto> getCalenderList(Long memberId, LocalDate date, String type);
}
