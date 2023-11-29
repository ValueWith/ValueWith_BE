package com.valuewith.tweaver.alert.repository;

import com.valuewith.tweaver.alert.dto.AlertResponseDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface AlertDsl {
  Slice<AlertResponseDto> getAlertsByMemberId(Long memberId, Pageable pageable);

  Long getAlertCountByMemberId(Long memberId);

  void checkAllByMemberId(Long memberId);
}
