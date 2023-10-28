package com.valuewith.tweaver.chat.repository;


import com.valuewith.tweaver.chat.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}