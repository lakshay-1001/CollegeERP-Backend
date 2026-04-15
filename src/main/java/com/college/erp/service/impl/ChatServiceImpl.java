package com.college.erp.service.impl;

import com.college.erp.entity.ChatMessage;
import com.college.erp.entity.User;
import com.college.erp.entity.enums.UserStatus;
import com.college.erp.repository.ChatMessageRepository;
import com.college.erp.repository.UserRepository;
import com.college.erp.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatMessageRepository chatRepository;
    private final UserRepository userRepository;

    @Override
    public ChatMessage sendMessage(Long senderId, Long receiverId, String message) {

        User sender = userRepository.findById(senderId)
                .orElseThrow(() -> new RuntimeException("Sender not found"));

        User receiver = userRepository.findById(receiverId)
                .orElseThrow(() -> new RuntimeException("Receiver not found"));

        // 🔥 YOUR CUSTOM RULE (IMPORTANT)
        if (sender.getStatus() == UserStatus.PENDING) {

            // allow only support
            if (!receiver.getRole().name().equals("SUPPORT")) {
                throw new RuntimeException("Pending users can only chat with support");
            }
        }

        ChatMessage chat = ChatMessage.builder()
                .sender(sender)
                .receiver(receiver)
                .message(message)
                .timestamp(LocalDateTime.now())
                .build();

        return chatRepository.save(chat);
    }

    @Override
    public List<ChatMessage> getConversation(Long user1, Long user2) {

        List<ChatMessage> messages = new ArrayList<>();

        messages.addAll(chatRepository.findBySenderIdAndReceiverId(user1, user2));
        messages.addAll(chatRepository.findByReceiverIdAndSenderId(user1, user2));

        // Optional: sort by time
        messages.sort((a, b) -> a.getTimestamp().compareTo(b.getTimestamp()));

        return messages;
    }

    @Override
    public Long getUnreadCount(Long userId) {
        return chatRepository.countByReceiverIdAndSeenFalse(userId);
    }

    @Override
    public void markAsRead(Long senderId, Long receiverId) {

        List<ChatMessage> messages =
                chatRepository.findBySenderIdAndReceiverId(senderId, receiverId);

        for (ChatMessage msg : messages) {
            if (!msg.getSeen()) {
                msg.setSeen(true);
                msg.setSeenAt(LocalDateTime.now());
            }
        }

        chatRepository.saveAll(messages);
    }
}