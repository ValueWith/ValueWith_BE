package com.valuewith.tweaver.message.repository;

import static com.valuewith.tweaver.message.entity.QMessage.message;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.valuewith.tweaver.constants.ApprovedStatus;
import com.valuewith.tweaver.message.dto.MessageDto;
import com.valuewith.tweaver.message.entity.Message;
import java.util.List;
import java.util.Optional;
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

  @Override
  public Optional<Message> findLastByChatRoom(Long chatRoomId) {
    return Optional.ofNullable(query
        .selectFrom(message)
        .where(message.chatRoom.chatRoomId.eq(chatRoomId))
        .orderBy(message.createdDateTime.desc())
        .fetchFirst());
  }
}
