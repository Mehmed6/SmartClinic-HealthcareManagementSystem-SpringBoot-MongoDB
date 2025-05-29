package com.doganmehmet.app.config;

import com.doganmehmet.app.repository.IUserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;

@Configuration
public class AppConfig {
    private final IUserRepository m_userRepository;

    public AppConfig(IUserRepository userRepository)
    {
        m_userRepository = userRepository;
    }

    @Bean
    public UserDetailsService userDetailsService()
    {
        return username -> m_userRepository.findByUsername((username))
                .orElseThrow(() -> new RuntimeException("User not found: " + username));
    }
}
