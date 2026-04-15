package com.college.erp.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "chat_messages")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ChatMessage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // ✅ Who sent
    @ManyToOne
    @JoinColumn(name = "sender_id", nullable = false)
    private User sender;

    // ✅ Who receives
    @ManyToOne
    @JoinColumn(name = "receiver_id", nullable = false)
    private User receiver;

    private String message;

    private LocalDateTime timestamp;

    @Column(nullable = false)
    private Boolean seen = false;

    private LocalDateTime seenAt;
}