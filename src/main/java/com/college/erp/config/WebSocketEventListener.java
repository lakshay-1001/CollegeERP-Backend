package com.college.erp.config;

import com.college.erp.security.JwtUtil;
import com.college.erp.service.UserPresenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.*;

@Component
@RequiredArgsConstructor
public class WebSocketEventListener {

    private final JwtUtil jwtUtil;
    private final UserPresenceService presenceService;

    @EventListener
    public void handleConnect(SessionConnectedEvent event) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String token = accessor.getFirstNativeHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {

            String email = jwtUtil.extractUsername(token.substring(7));

            presenceService.setOnline(email);

            System.out.println("🟢 ONLINE: " + email);
        }
    }

    @EventListener
    public void handleDisconnect(SessionDisconnectEvent event) {

        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());

        String token = accessor.getFirstNativeHeader("Authorization");

        if (token != null && token.startsWith("Bearer ")) {

            String email = jwtUtil.extractUsername(token.substring(7));

            presenceService.setOffline(email);

            System.out.println("🔴 OFFLINE: " + email);
        }
    }
}