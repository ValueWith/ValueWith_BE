package com.valuewith.tweaver.message.service;

import com.valuewith.tweaver.chat.entity.ChatRoom;
import com.valuewith.tweaver.chat.repository.ChatRoomRepository;
import com.valuewith.tweaver.member.entity.Member;
import com.valuewith.tweaver.message.dto.MessageDto;
import com.valuewith.tweaver.message.entity.Message;
import com.valuewith.tweaver.message.repository.MessageRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

  private final ChatRoomRepository chatRoomRepository;
  private final MessageRepository messageRepository;

  @Transactional
  public void deleteMessage(Long tripGroupId) {
    ChatRoom chatRoom = chatRoomRepository.findByTripGroupTripGroupId(tripGroupId)
        .orElseThrow(() -> new RuntimeException("삭제 하려는 메세지의 채팅방이 존재하지 않습니다."));
    messageRepository.deleteByChatRoomChatRoomId(chatRoom.getChatRoomId());
  }

  public MessageDto createMessage(ChatRoom chatRoom, Member sender, String content) {
    Message createdMessage = Message.from(chatRoom, sender, content);

    messageRepository.save(createdMessage);

    return MessageDto.from(createdMessage);
  }

  public List<MessageDto> findAllByMessageList(Long chatRoomId) {
    return messageRepository.findAllByChatRoom_ChatRoomId(chatRoomId)
        .stream()
        .map(MessageDto::from)
        .collect(Collectors.toList());
  }

  public MessageDto findLastMessage(Long chatRoomId) {
    Optional<Message> lastByChatRoom = messageRepository.findLastByChatRoom(chatRoomId);
    if(lastByChatRoom.isEmpty()) {
      // TODO: 메세지가 없는 채팅방일 경우 어떻게 할지 정해야 함.
      return null;
    }
    return MessageDto.from(lastByChatRoom.get());
  }
}
