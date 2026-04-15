package com.college.erp.repository;

import com.college.erp.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findBySenderIdAndReceiverId(Long senderId, Long receiverId);

    List<ChatMessage> findByReceiverIdAndSenderId(Long receiverId, Long senderId);

    // 🔥 unread count
    Long countByReceiverIdAndSeenFalse(Long receiverId);

    // 🔥 get unread messages
    List<ChatMessage> findByReceiverIdAndSeenFalse(Long receiverId);
}