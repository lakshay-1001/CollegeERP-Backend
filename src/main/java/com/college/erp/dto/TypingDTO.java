package com.college.erp.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TypingDTO {

    private Long userId;     // sender
    private Long receiverId; // receiver
    private boolean typing;
}