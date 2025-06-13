package com.doganmehmet.app.service;

import com.doganmehmet.app.dto.request.RegisterRequest;
import com.doganmehmet.app.dto.response.RegisterResponse;
import com.doganmehmet.app.entity.User;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.mapper.IUserMapper;
import com.doganmehmet.app.repository.IRoleRepository;
import com.doganmehmet.app.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RegisterService {
    private final IUserRepository m_userRepository;
    private final IRoleRepository m_roleRepository;
    private final PasswordEncoder m_passwordEncoder;
    private final IUserMapper m_userMapper;

    public RegisterResponse register(RegisterRequest registerRequest)
    {
        if (m_userRepository.findByUsername(registerRequest.getUsername()).isPresent())
            throw new ApiException(MyError.USER_ALREADY_EXISTS);

        var user = m_userMapper.toUser(registerRequest);
        user.setPassword(m_passwordEncoder.encode(registerRequest.getPassword()));

        var roles = registerRequest.getRoles().stream()
                .peek(userRole -> {
                    if (userRole.getName().equalsIgnoreCase("ADMIN"))
                        throw new ApiException(MyError.ADMIN_ROLE_NOT_ALLOWED);
                })
                        .map(roleName -> m_roleRepository.findByName(roleName.getName().toUpperCase())
                                .orElseThrow(() -> new ApiException(MyError.ROLE_NOT_FOUND, roleName.getName())))
                                .collect(Collectors.toSet());

        user.setRoles(roles);
        m_userRepository.save(user);

        return new RegisterResponse(user.getUsername(),
                                   user.getEmail(),
                                   user.getRoles());
    }
}
