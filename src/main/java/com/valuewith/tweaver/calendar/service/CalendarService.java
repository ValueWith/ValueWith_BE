package com.valuewith.tweaver.calendar.service;

import com.valuewith.tweaver.calendar.dto.CalendarDetailResponseDto;
import com.valuewith.tweaver.calendar.dto.CalendarResponseDto;
import com.valuewith.tweaver.calendar.repository.CalendarRepository;
import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.ListUtils;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class CalendarService {
  private final CalendarRepository calendarRepository;

  public List<CalendarResponseDto> getCalendarList(Long memberId, LocalDate date, String type){
    List<CalendarResponseDto> listFromTripGroup = calendarRepository.getCalenderListFromTripGroup(memberId, date, type);
    List<CalendarResponseDto> listFromGroupMember = calendarRepository.getCalenderListFromGroupMember(memberId, date, type);
    List<CalendarResponseDto> sortedList = Stream.concat(listFromTripGroup.stream(), listFromGroupMember.stream())
        .sorted(Comparator.comparing(CalendarResponseDto::getTripDate))
        .collect(Collectors.toList());
    return sortedList;
  }

  public List<CalendarDetailResponseDto> getCalendarDetail(Long memberId, LocalDate date, Long tripGroupId) {
    return calendarRepository.getCalendarDetail(memberId, date, tripGroupId);
  }
}
