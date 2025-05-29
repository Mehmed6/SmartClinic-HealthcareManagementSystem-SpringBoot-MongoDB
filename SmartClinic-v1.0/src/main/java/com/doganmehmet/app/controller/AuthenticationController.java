package com.doganmehmet.app.controller;

import com.doganmehmet.app.dto.request.AuthRequest;
import com.doganmehmet.app.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthService m_authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest)
    {
        var authResponse = m_authService.login(authRequest);
        return ResponseEntity.ok(authResponse);
    }
}
