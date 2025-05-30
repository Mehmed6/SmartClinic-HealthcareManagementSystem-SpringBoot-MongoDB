package com.doganmehmet.app.helper;

import com.doganmehmet.app.dto.request.PatientSaveBySecretaryRequest;
import com.doganmehmet.app.entity.User;
import com.doganmehmet.app.entity.UserProfile;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.mapper.IUserMapper;
import com.doganmehmet.app.mapper.IUserProfileMapper;
import com.doganmehmet.app.repository.IUserProfileRepository;
import com.doganmehmet.app.repository.IUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class CurrentUserProfileHelper {
    private final IUserProfileRepository m_userProfileRepository;
    private final IUserRepository m_userRepository;
    private final IUserProfileMapper m_userProfileMapper;
    private final IUserMapper m_userMapper;
    private final PasswordEncoder m_passwordEncoder;

    public User getCurrentUser()
    {
        var username = SecurityContextHolder.getContext().getAuthentication().getName();
        return m_userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(MyError.USER_NOT_FOUND));
    }
    public User getUserByUsername(String username)
    {
        return m_userRepository.findByUsername(username)
                .orElseThrow(() -> new ApiException(MyError.USER_NOT_FOUND));
    }
    public User getUserByEmail(String email)
    {
        return m_userRepository.findByEmail(email)
                .orElseThrow(() -> new ApiException(MyError.USER_NOT_FOUND));
    }

    public UserProfile getUserProfileByUser(User user)
    {
        return m_userProfileRepository.findByUser(user)
                .orElseThrow(() -> new ApiException(MyError.USER_PROFILE_NOT_FOUND));
    }

    public UserProfile getCurrentUserProfile()
    {
        return getUserProfileByUser(getCurrentUser());
    }

    public UserProfile saveUserProfileBySecretary(PatientSaveBySecretaryRequest request)
    {
        var user = m_userMapper.toUser(request);
        user.setPassword(m_passwordEncoder.encode(UUID.randomUUID().toString()));

        var userProfile = m_userProfileMapper.toUserProfile(request);
        userProfile.setUser(user);

        m_userRepository.save(user);
        return m_userProfileRepository.save(userProfile);
    }
}
