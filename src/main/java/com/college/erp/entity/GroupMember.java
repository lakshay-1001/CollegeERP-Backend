package com.college.erp.entity;

import com.college.erp.entity.enums.GroupRole;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class GroupMember {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private ChatGroup group;

    @ManyToOne
    private User user;

    @Enumerated(EnumType.STRING)
    private GroupRole role; // ADMIN / TEACHER / STUDENT

    private int unreadCount;

    private LocalDateTime joinedAt;
}
