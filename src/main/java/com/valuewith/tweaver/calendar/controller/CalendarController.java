package com.valuewith.tweaver.calendar.controller;

import com.valuewith.tweaver.calendar.dto.CalendarDetailResponseDto;
import com.valuewith.tweaver.calendar.dto.CalendarResponseDto;
import com.valuewith.tweaver.calendar.service.CalendarService;
import com.valuewith.tweaver.commons.security.service.TokenService;
import com.valuewith.tweaver.config.SwaggerConfig;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.time.LocalDate;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = {SwaggerConfig.CALENDAR_TAG})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/calendar/*")
public class CalendarController {
  private final TokenService tokenService;
  private final CalendarService calendarService;

  @ApiOperation(value = "달별 그룹 정보 API",
      notes = "date : 날짜\n"
          + "mini : false(큰 달력) / true(포스트 등록용 작은 달력)\n")
  @GetMapping("list")
  public ResponseEntity<List<CalendarResponseDto>> getCalenderList(
      @RequestHeader("Authorization") String token,
      @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date,
      @RequestParam (defaultValue = "big") String type
  ) {
    List<CalendarResponseDto> calendarList = calendarService.getCalendarList(tokenService.getMemberId(token), date, type);
    return ResponseEntity.ok(calendarList);
  }

  @ApiOperation(value = "날짜, 그룹 별 후기 정보 API",
      notes = "date : 날짜\n")
  @GetMapping("detail")
  public ResponseEntity<List<CalendarDetailResponseDto>> getCalenderDetail(
      @RequestHeader("Authorization") String token,
      @RequestParam @DateTimeFormat(iso = ISO.DATE) LocalDate date,
      @RequestParam Long tripGroupId
  ) {
    List<CalendarDetailResponseDto> calendarDetailList = calendarService.getCalendarDetail(tokenService.getMemberId(token), date, tripGroupId);
    return ResponseEntity.ok(calendarDetailList);
  }
}
