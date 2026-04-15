package com.college.erp.service;

import com.college.erp.dto.GroupMessageDTO;

import java.security.Principal;

public interface GroupChatService {

    void sendMessage(GroupMessageDTO dto, Principal principal);

    void resetUnread(Long groupId, Principal principal);

}
