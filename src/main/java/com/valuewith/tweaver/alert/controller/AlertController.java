package com.valuewith.tweaver.alert.controller;

import com.valuewith.tweaver.alert.dto.AlertResponseDto;
import com.valuewith.tweaver.alert.service.AlertService;
import com.valuewith.tweaver.commons.security.service.TokenService;
import com.valuewith.tweaver.config.SwaggerConfig;
import com.valuewith.tweaver.member.entity.Member;
import com.valuewith.tweaver.member.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

@Api(tags = {SwaggerConfig.ALERT_TAG})
@RestController
@RequestMapping("/alert")
@RequiredArgsConstructor
public class AlertController {
  private final MemberService memberService;
  private final AlertService alertService;
  private final TokenService tokenService;

  /**
   * 로그인 한 유저 알람 sse 연결
   */
  @ApiOperation(value = "알림 구독 API")
  @GetMapping(value = "/subscribe", produces = "text/event-stream")
  public ResponseEntity<SseEmitter> subscribe(
      @RequestHeader("Authorization") String token,
      @RequestHeader(value = "Last-Event-ID", required = false, defaultValue = "") String lastEventId) {
    Member member = memberService.findMemberByEmail(tokenService.getMemberEmail(token));
    // 서비스를 통해 생성된 SseEmitter를 반환
    return ResponseEntity.ok(alertService.subscribe(member.getMemberId(), lastEventId));
  }

  // 내 알림 목록 조회
  @ApiOperation(value = "내 알림목록 조회 API")
  @GetMapping
  public ResponseEntity<Slice<AlertResponseDto>> alerts(@RequestHeader("Authorization") String token,
      @PageableDefault(size = 20) Pageable pageable) {
    Member member = memberService.findMemberByEmail(tokenService.getMemberEmail(token));

    return ResponseEntity.ok(alertService.getAlerts(member.getMemberId(), pageable));
  }

  /**
   * 알림 읽음 처리
   * @param alarmId
   */
  @ApiOperation(value = "알림 읽음 처리 API")
  @PatchMapping("/{alertId}")
  public ResponseEntity<Long> check(
      @PathVariable("alertId") Long alarmId,
      @RequestHeader("Authorization") String token
  ) {
    Member member = memberService.findMemberByEmail(tokenService.getMemberEmail(token));
    Long alertCnt = alertService.check(member.getMemberId(), alarmId);
    return ResponseEntity.ok(alertCnt);
  }

  /**
   * 전체 알림 읽음 처리
   */
  @ApiOperation(value = "전체 알림 읽음 처리 API")
  @PatchMapping("/all")
  public ResponseEntity<String> allCheck(
      @RequestHeader("Authorization") String token
  ) {
    Member member = memberService.findMemberByEmail(tokenService.getMemberEmail(token));
    alertService.allCheck(member.getMemberId());
    return ResponseEntity.ok("ok");
  }

  /**
   * 알림 삭제 처리
   * @param alarmId
   */
  @ApiOperation(value = "알림 삭제 처리 API")
  @DeleteMapping("/{alertId}")
  public ResponseEntity<Long> delete(
      @PathVariable("alertId") Long alarmId,
      @RequestHeader("Authorization") String token
  ) {
    Member member = memberService.findMemberByEmail(tokenService.getMemberEmail(token));
    Long alertCnt = alertService.delete(member.getMemberId(), alarmId);
    return ResponseEntity.ok(alertCnt);
  }

}
