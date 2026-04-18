package com.college.erp.service.impl;

import com.college.erp.dto.AuthRequest;
import com.college.erp.dto.AuthResponse;
import com.college.erp.entity.Student;
import com.college.erp.entity.Teacher;
import com.college.erp.entity.User;
import com.college.erp.entity.enums.Role;
import com.college.erp.entity.enums.UserStatus;
import com.college.erp.repository.StudentRepository;
import com.college.erp.repository.TeacherRepository;
import com.college.erp.repository.UserRepository;
import com.college.erp.security.JwtUtil;
import com.college.erp.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;

    @Override
    public AuthResponse register(AuthRequest request) {

        // Check if user already exists
        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "User already exists");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .status(UserStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();

        User savedUser = userRepository.save(user);

        // 🔥 AUTO CREATE STUDENT PROFILE
        if (savedUser.getRole() == Role.STUDENT) {

            Student student = Student.builder()
                    .user(savedUser)
                    .build();

            studentRepository.save(student);
        }

        // 🔥 TEACHER AUTO CREATE
        if (savedUser.getRole() == Role.TEACHER) {

            teacherRepository.findByUserId(savedUser.getId())
                    .ifPresent(t -> {
                        throw new ResponseStatusException(HttpStatus.CONFLICT, "Teacher already exists");
                    });

            Teacher teacher = Teacher.builder()
                    .user(savedUser)
                    .build();

            teacherRepository.save(teacher);
        }

        return new AuthResponse(null, "User registered successfully",
                savedUser.getEmail(),
                savedUser.getRole(),
                savedUser.getStatus());
    }

    @Override
    public AuthResponse login(AuthRequest request) {

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        }

        // ✅ Allow login even if PENDING
        String token = jwtUtil.generateToken(user.getEmail());

        String message = "Login successful";

        if (user.getStatus() == UserStatus.PENDING) {
            message = "Login successful (Limited Access - Pending Approval)";
        }

        return new AuthResponse(
                token,
                message,
                user.getEmail(),
                user.getRole(),
                user.getStatus()
        );
    }
}