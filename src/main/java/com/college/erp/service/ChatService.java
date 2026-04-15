package com.college.erp.service;

import com.college.erp.entity.ChatMessage;

import java.util.List;

public interface ChatService {

    ChatMessage sendMessage(Long senderId, Long receiverId, String message);

    List<ChatMessage> getConversation(Long user1, Long user2);

    Long getUnreadCount(Long userId);

    void markAsRead(Long senderId, Long receiverId);
}