package com.doganmehmet.app.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthResponse {
    private String jwtToken;
    private String refreshToken;
}
