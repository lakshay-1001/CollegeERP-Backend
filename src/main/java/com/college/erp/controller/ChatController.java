package com.college.erp.controller;

import com.college.erp.entity.ChatMessage;
import com.college.erp.entity.User;
import com.college.erp.entity.enums.Role;
import com.college.erp.entity.enums.UserStatus;
import com.college.erp.repository.UserRepository;
import com.college.erp.security.JwtUtil;
import com.college.erp.security.RequireRole;
import com.college.erp.security.RequireStatus;
import com.college.erp.service.ChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    @PostMapping("/send")
    @RequireRole({Role.ADMIN, Role.TEACHER, Role.STUDENT, Role.SUPPORT})
    public ChatMessage send(
            @RequestParam Long receiverId,
            @RequestParam String message,
            @RequestHeader("Authorization") String token
    ) {
        String email = jwtUtil.extractUsername(token.substring(7));

        User sender = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return chatService.sendMessage(sender.getId(), receiverId, message);
    }

    // ✅ Get Conversation
    @GetMapping("/conversation")
    @RequireRole({Role.ADMIN, Role.TEACHER, Role.STUDENT})
    @RequireStatus({UserStatus.VERIFIED})
    public List<ChatMessage> getConversation(
            @RequestParam Long user1,
            @RequestParam Long user2
    ) {
        return chatService.getConversation(user1, user2);
    }

    @GetMapping("/unread")
    public Long getUnread(@RequestHeader("Authorization") String token) {

        String email = jwtUtil.extractUsername(token.substring(7));

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return chatService.getUnreadCount(user.getId());
    }

    @PostMapping("/read")
    public String markAsRead(
            @RequestParam Long senderId,
            @RequestHeader("Authorization") String token
    ) {
        String email = jwtUtil.extractUsername(token.substring(7));

        User receiver = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        chatService.markAsRead(senderId, receiver.getId());

        Long unreadCount = chatService.getUnreadCount(receiver.getId());

        messagingTemplate.convertAndSendToUser(
                receiver.getId().toString(),
                "/queue/unread",
                unreadCount
        );

        return "Marked as read";
    }
}