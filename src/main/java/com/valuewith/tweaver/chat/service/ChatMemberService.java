package com.valuewith.tweaver.chat.service;

import com.valuewith.tweaver.chat.entity.ChatRoom;
import com.valuewith.tweaver.chat.repository.ChatRoomRepository;
import com.valuewith.tweaver.constants.ApprovedStatus;
import com.valuewith.tweaver.groupMember.entity.GroupMember;
import com.valuewith.tweaver.groupMember.repository.GroupMemberRepository;
import com.valuewith.tweaver.message.dto.MessageResponseDto;
import com.valuewith.tweaver.message.repository.MessageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatMemberService {

  private final ChatRoomRepository chatRoomRepository;
  private final GroupMemberRepository groupMemberRepository;
  private final MessageRepository messageRepository;

  public String enterChatRoom(ChatRoom chatroom, GroupMember groupMember) {
    if (groupMember.getApprovedStatus().equals(ApprovedStatus.PENDING)) {
      throw new RuntimeException("승인 대기중인 회원은 채팅방에 입장할 수 없습니다.");
    }
    if (groupMember.getApprovedStatus().equals(ApprovedStatus.REJECTED)) {
      throw new RuntimeException("입장할 수 없는 회원입니다.");
    }
    GroupMember newGroupMember = GroupMember.enterChatRoom(chatroom, groupMember);
    chatRoomRepository.save(newGroupMember.getChatRoom());

    return chatroom.getTitle() + " 방 참여";
  }

  public String exitChatRoom(ChatRoom chatRoom, GroupMember groupMember) {
    groupMemberRepository.delete(groupMember);

    return chatRoom.getTitle() + " 방 퇴장";
  }

  public Slice<MessageResponseDto> enterChatRoom(Long chatRoomId, Pageable pageable) {
    return messageRepository.getMessageByChatRoomId(chatRoomId, pageable);
  }
}
