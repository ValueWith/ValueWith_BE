package com.valuewith.tweaver.message.repository;

import static com.valuewith.tweaver.message.entity.QMessage.message;

import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MessageDslImpl implements MessageDsl{

  private final JPAQueryFactory query;

  @Override
  public void deleteByChatRoomChatRoomId(Long chatRoomId) {
    query
        .update(message)
        .set(message.isDeleted, true)
        .where(message.chatRoom.chatRoomId.eq(chatRoomId)
            .and(message.isDeleted.eq(Boolean.FALSE)))
        .execute();
  }
}
