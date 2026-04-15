package com.college.erp.controller;

import com.college.erp.dto.ChatMessageDTO;
import com.college.erp.dto.TypingDTO;
import com.college.erp.entity.ChatMessage;
import com.college.erp.entity.User;
import com.college.erp.repository.UserRepository;
import com.college.erp.security.JwtUtil;
import com.college.erp.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ChatWebSocketController {

    private final ChatService chatService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @MessageMapping("/chat")
    public void sendMessage(
            @Payload ChatMessageDTO dto,
            @Header("Authorization") String token
    ) {
        String email = jwtUtil.extractUsername(token.substring(7));

        User sender = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        ChatMessage saved = chatService.sendMessage(
                sender.getId(),
                dto.getReceiverId(),
                dto.getMessage()
        );

        // ✅ Send message
        messagingTemplate.convertAndSendToUser(
                dto.getReceiverId().toString(),
                "/queue/messages",
                saved
        );

        // 🔥 SEND UPDATED UNREAD COUNT
        Long unreadCount = chatService.getUnreadCount(dto.getReceiverId());

        messagingTemplate.convertAndSendToUser(
                dto.getReceiverId().toString(),
                "/queue/unread",
                unreadCount
        );
    }

    @MessageMapping("/typing")
    public void typing(
            @Payload TypingDTO dto,
            @Header("Authorization") String token
    ) {
        String email = jwtUtil.extractUsername(token.substring(7));

        User sender = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        messagingTemplate.convertAndSendToUser(
                dto.getReceiverId().toString(),
                "/queue/typing",
                TypingDTO.builder()
                        .userId(sender.getId())
                        .typing(dto.isTyping())
                        .build()
        );
    }
}