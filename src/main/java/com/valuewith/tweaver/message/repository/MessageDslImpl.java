package com.valuewith.tweaver.message.repository;

import static com.valuewith.tweaver.alert.entity.QAlert.alert;
import static com.valuewith.tweaver.groupMember.entity.QGroupMember.*;
import static com.valuewith.tweaver.member.entity.QMember.*;
import static com.valuewith.tweaver.message.entity.QMessage.message;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.valuewith.tweaver.alert.dto.AlertResponseDto;
import com.valuewith.tweaver.constants.ApprovedStatus;
import com.valuewith.tweaver.groupMember.entity.QGroupMember;
import com.valuewith.tweaver.member.entity.QMember;
import com.valuewith.tweaver.message.dto.MessageDto;
import com.valuewith.tweaver.message.dto.MessageResponseDto;
import com.valuewith.tweaver.message.entity.Message;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

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

  @Override
  public Slice<MessageResponseDto> getMessageByChatRoomId(Long chatRoomId, Pageable pageable) {
    List<MessageResponseDto> messages = query.select(
            Projections.fields(MessageResponseDto.class,
                message.messageId,
                member.memberId,
                member.nickName,
                member.email,
                member.profileUrl,
                message.content,
                message.createdDateTime.as("createdAt")
            )
        ).from(message)
        .distinct()
        .innerJoin(member).on(message.member.memberId.eq(member.memberId))
        .leftJoin(groupMember).on(member.memberId.eq(groupMember.member.memberId))
        .where(
            message.chatRoom.chatRoomId.eq(chatRoomId),
            groupMember.approvedStatus.eq(ApprovedStatus.APPROVED),  // approved 상태인 경우
            groupMember.isDeleted.eq(false)            // isDeleted가 false인 경우
        )
        .orderBy(message.createdDateTime.desc())
        .offset(pageable.getOffset())
        .limit(pageable.getPageSize() + 1)
        .fetch();

    boolean hasNext = false;
    if (messages.size() > pageable.getPageSize()) {
      messages.remove(pageable.getPageSize());
      hasNext = true;
    }

    return new SliceImpl<>(messages, pageable, hasNext);
  }
}
