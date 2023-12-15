package com.valuewith.tweaver.chat.dto;

import com.valuewith.tweaver.chat.entity.ChatRoom;
import com.valuewith.tweaver.message.dto.MessageDto;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatRoomDto2 {
  private Long chatRoomId;
  private Long tripGroupId;
  private String title;
  private MessageDto lastMessage;

  public static ChatRoomDto2 from(ChatRoom chatRoom, MessageDto lastMessage) {
    return ChatRoomDto2.builder()
        .chatRoomId(chatRoom.getChatRoomId())
        .tripGroupId(chatRoom.getTripGroup().getTripGroupId())
        .title(chatRoom.getTitle())
        .lastMessage(lastMessage)
        .build();
  }
}
