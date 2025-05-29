package com.doganmehmet.app.dto.request;

import com.doganmehmet.app.entity.Role;
import lombok.Getter;

import java.util.Set;

@Getter
public class RegisterRequest {
    private String username;
    private String password;
    private String email;
    private Set<Role> roles;
}
