package com.college.erp.controller;

import com.college.erp.dto.GroupMessageDTO;
import com.college.erp.service.GroupChatService;
import lombok.RequiredArgsConstructor;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class GroupChatWebSocketController {

    private final GroupChatService groupChatService;

    @MessageMapping("/group.sendMessage")
    public void sendMessage(GroupMessageDTO dto, Principal principal) {
        groupChatService.sendMessage(dto, principal);
    }
}
