package com.doganmehmet.app.config;

import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
@RequiredArgsConstructor
public class AppConfig {
    private final IUserRepository m_userRepository;

    @Bean
    public UserDetailsService userDetailsService()
    {
        return username -> m_userRepository.findByUsername((username))
                .orElseThrow(() -> new ApiException(MyError.USER_NOT_FOUND));
    }
}
