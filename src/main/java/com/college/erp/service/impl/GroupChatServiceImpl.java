package com.college.erp.service.impl;

import com.college.erp.dto.GroupMessageDTO;
import com.college.erp.dto.GroupMessageResponse;
import com.college.erp.dto.GroupUnreadDTO;
import com.college.erp.entity.GroupMember;
import com.college.erp.entity.GroupMessage;
import com.college.erp.entity.User;
import com.college.erp.entity.enums.GroupRole;
import com.college.erp.repository.GroupMemberRepository;
import com.college.erp.repository.GroupMessageRepository;
import com.college.erp.repository.UserRepository;
import com.college.erp.service.GroupChatService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.Principal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class GroupChatServiceImpl implements GroupChatService {

    private final GroupMemberRepository memberRepo;
    private final GroupMessageRepository messageRepo;
    private final SimpMessagingTemplate messagingTemplate;
    private final UserRepository userRepo;

    @Override
    @Transactional
    public void sendMessage(GroupMessageDTO dto, Principal principal) {

        // ✅ 1. Validate message
        if (dto.getContent() == null || dto.getContent().isBlank()) {
            throw new RuntimeException("Message cannot be empty");
        }
        if (dto.getGroupId() == null) {
            throw new RuntimeException("GroupId is required");
        }

        // ✅ 2. Get sender
        User sender = getUserFromPrincipal(principal);

        // ✅ 3. Check membership + fetch group
        GroupMember member = memberRepo
                .findByGroupIdAndUser(dto.getGroupId(), sender)
                .orElseThrow(() -> new RuntimeException("Not a group member"));

        // 🔐 4. Role check
        if (member.getRole() == GroupRole.STUDENT) {
            throw new RuntimeException("Students cannot send messages");
        }

        // ✅ 5. Save message
        GroupMessage message = new GroupMessage();
        message.setGroup(member.getGroup());
        message.setSender(sender);
        message.setContent(dto.getContent());
        message.setTimestamp(LocalDateTime.now());

        messageRepo.save(message);

        // ✅ 6. Convert to response DTO
        GroupMessageResponse response = mapToResponse(message);

        // ✅ 7. Broadcast message
        messagingTemplate.convertAndSend(
                "/topic/group/" + dto.getGroupId(),
                response
        );

        // ✅ 8. Update unread count
        updateUnreadCounts(member.getGroup().getId(), sender.getId());

        log.info("Message sent in group {} by user {}", dto.getGroupId(), sender.getId());
    }

    // ==========================
    // 🔔 UNREAD SYSTEM
    // ==========================
    private void updateUnreadCounts(Long groupId, Long senderId) {

        List<GroupMember> members = memberRepo.findAllByGroupId(groupId);

        for (GroupMember m : members) {

            if (!m.getUser().getId().equals(senderId)) {

                m.setUnreadCount(m.getUnreadCount() + 1);
                memberRepo.save(m);

                // 🔔 Real-time unread update
                messagingTemplate.convertAndSendToUser(
                        m.getUser().getEmail(), // or email
                        "/queue/group-unread",
                        GroupUnreadDTO.builder()
                                .groupId(groupId)
                                .unreadCount(m.getUnreadCount())
                                .build()
                );
            }
        }
    }

    // ==========================
    // 🔄 RESET UNREAD
    // ==========================
    @Override
    @Transactional
    public void resetUnread(Long groupId, Principal principal) {

        User user = getUserFromPrincipal(principal);

        GroupMember member = memberRepo
                .findByGroupIdAndUser(groupId, user)
                .orElseThrow(() -> new RuntimeException("Not a member"));

        member.setUnreadCount(0);
        memberRepo.save(member);
    }

    // ==========================
    // 👤 USER FROM JWT
    // ==========================
    private User getUserFromPrincipal(Principal principal) {
        return userRepo.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // ==========================
    // 🔄 MAPPER
    // ==========================
    private GroupMessageResponse mapToResponse(GroupMessage message) {
        return GroupMessageResponse.builder()
                .id(message.getId())
                .groupId(message.getGroup().getId())
                .senderId(message.getSender().getId())
                .senderName(message.getSender().getName())
                .content(message.getContent())
                .timestamp(message.getTimestamp())
                .build();
    }
}
