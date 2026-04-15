package com.college.erp.controller;

import com.college.erp.service.UserPresenceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/presence")
@RequiredArgsConstructor
public class PresenceController {

    private final UserPresenceService presenceService;

    @GetMapping("/is-online")
    public boolean isOnline(@RequestParam String email) {
        return presenceService.isOnline(email);
    }
}