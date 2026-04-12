package com.college.erp.controller;

import com.college.erp.entity.enums.Role;
import com.college.erp.entity.enums.UserStatus;
import com.college.erp.security.RequireRole;
import com.college.erp.security.RequireStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/students")
public class StudentController {

    @GetMapping
    @RequireRole({Role.ADMIN, Role.TEACHER})
    @RequireStatus({UserStatus.VERIFIED})
    public String getStudents() {
        return "Students data";
    }
}
