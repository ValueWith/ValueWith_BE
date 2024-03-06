package com.valuewith.tweaver.calendar.service;

import com.valuewith.tweaver.calendar.dto.CalendarDetailResponseDto;
import com.valuewith.tweaver.calendar.dto.CalendarResponseDto;
import com.valuewith.tweaver.calendar.repository.CalendarRepository;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {
  private final CalendarRepository calendarRepository;

  public List<CalendarResponseDto> getCalendarList(Long memberId, LocalDate date, String type){
    return calendarRepository.getCalenderList(memberId, date, type);
  }

  public List<CalendarDetailResponseDto> getCalendarDetail(Long memberId, LocalDate date, Long tripGroupId) {
    return calendarRepository.getCalendarDetail(memberId, date, tripGroupId);
  }
}
