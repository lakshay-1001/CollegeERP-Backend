package com.college.erp.security;

import com.college.erp.entity.User;
import com.college.erp.entity.enums.Role;
import com.college.erp.entity.enums.UserStatus;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.Arrays;

@Component
public class AccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request,
                             HttpServletResponse response,
                             Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod method)) {
            return true;
        }

        User user = (User) request.getAttribute("user");

        // If no user (not authenticated)
        if (user == null) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return false;
        }

        // 🔐 Role Check
        RequireRole roleAnnotation = method.getMethodAnnotation(RequireRole.class);
        if (roleAnnotation != null) {
            Role[] allowedRoles = roleAnnotation.value();

            if (Arrays.stream(allowedRoles).noneMatch(r -> r == user.getRole())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        }

        // 🔐 Status Check
        RequireStatus statusAnnotation = method.getMethodAnnotation(RequireStatus.class);
        if (statusAnnotation != null) {
            UserStatus[] allowedStatus = statusAnnotation.value();

            if (Arrays.stream(allowedStatus).noneMatch(s -> s == user.getStatus())) {
                response.setStatus(HttpServletResponse.SC_FORBIDDEN);
                return false;
            }
        }

        return true;
    }
}
