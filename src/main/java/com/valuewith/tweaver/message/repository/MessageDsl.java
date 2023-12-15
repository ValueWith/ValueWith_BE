package com.valuewith.tweaver.message.repository;

import com.valuewith.tweaver.message.dto.MessageResponseDto;
import com.valuewith.tweaver.message.entity.Message;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface MessageDsl {
  void deleteByChatRoomChatRoomId(Long chatRoomId);

  Optional<Message> findLastByChatRoom(Long chatRoomId);

  Slice<MessageResponseDto> getMessageByChatRoomId(Long chatRoomId, Pageable pageable);
}
