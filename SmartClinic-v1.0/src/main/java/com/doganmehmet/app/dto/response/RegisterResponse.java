package com.doganmehmet.app.dto.response;

import com.doganmehmet.app.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Set;

@Getter
@AllArgsConstructor
public class RegisterResponse {

    private String username;
    private String email;
    private Set<Role> roles;
}
