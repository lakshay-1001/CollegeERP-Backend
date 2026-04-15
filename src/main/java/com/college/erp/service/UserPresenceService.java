package com.college.erp.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;

@Service
public class UserPresenceService {

    // 🔥 thread-safe map
    private final ConcurrentHashMap<String, Boolean> onlineUsers = new ConcurrentHashMap<>();

    public void setOnline(String email) {
        onlineUsers.put(email, true);
    }

    public void setOffline(String email) {
        onlineUsers.remove(email);
    }

    public boolean isOnline(String email) {
        return onlineUsers.containsKey(email);
    }

    public ConcurrentHashMap<String, Boolean> getAllOnlineUsers() {
        return onlineUsers;
    }
}