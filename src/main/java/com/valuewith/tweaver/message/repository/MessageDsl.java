package com.valuewith.tweaver.message.repository;

import com.valuewith.tweaver.message.entity.Message;
import java.util.Optional;

public interface MessageDsl {
  void deleteByChatRoomChatRoomId(Long chatRoomId);

  Optional<Message> findLastByChatRoom(Long chatRoomId);
}
