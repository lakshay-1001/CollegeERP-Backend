package com.college.erp.repository;

import com.college.erp.entity.GroupMember;
import com.college.erp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface GroupMemberRepository extends JpaRepository<GroupMember, Long> {

    @Query("SELECT gm FROM GroupMember gm JOIN FETCH gm.group WHERE gm.group.id = :groupId AND gm.user = :user")
    Optional<GroupMember> findByGroupIdAndUser(@Param("groupId") Long groupId,
                                               @Param("user") User user);

    List<GroupMember> findAllByGroupId(Long groupId);

}
