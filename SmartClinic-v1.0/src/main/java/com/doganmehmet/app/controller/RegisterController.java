package com.doganmehmet.app.controller;

import com.doganmehmet.app.dto.request.RegisterRequest;
import com.doganmehmet.app.service.RegisterService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/register")
@RequiredArgsConstructor
public class RegisterController {
    private final RegisterService m_registerService;

    @PostMapping
    public ResponseEntity<?> register(@RequestBody RegisterRequest registerRequest)
    {
        var response = m_registerService.register(registerRequest);
        return ResponseEntity.ok(response);
    }
}
