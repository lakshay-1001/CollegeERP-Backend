package com.college.erp.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupMessageResponse {

    private Long id;
    private Long groupId;

    private Long senderId;
    private String senderName;

    private String content;

    private LocalDateTime timestamp;
}
