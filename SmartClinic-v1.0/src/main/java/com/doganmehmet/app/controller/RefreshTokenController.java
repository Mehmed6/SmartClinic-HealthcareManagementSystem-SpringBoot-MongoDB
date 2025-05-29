package com.doganmehmet.app.controller;

import com.doganmehmet.app.service.RefreshTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
public class RefreshTokenController {

    public final RefreshTokenService m_refreshTokenService;

    @PostMapping("/refresh-token")
    public ResponseEntity<?> refreshToken(@RequestBody String refreshToken)
    {
        return ResponseEntity.ok(m_refreshTokenService.refreshToken(refreshToken));
    }
}
