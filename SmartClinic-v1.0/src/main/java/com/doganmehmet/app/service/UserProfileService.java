package com.doganmehmet.app.service;

import com.doganmehmet.app.dto.request.UserProfileRequest;
import com.doganmehmet.app.dto.response.UserProfileDTO;
import com.doganmehmet.app.entity.User;
import com.doganmehmet.app.entity.UserProfile;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.helper.CurrentUserProfileHelper;
import com.doganmehmet.app.mapper.IUserProfileMapper;
import com.doganmehmet.app.repository.IUserProfileRepository;
import com.doganmehmet.app.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final IUserProfileRepository m_userProfileRepository;
    private final IUserProfileMapper m_userProfileMapper;
    private final CurrentUserProfileHelper m_currentUserProfileHelper;

    public UserProfileDTO save(UserProfileRequest request)
    {
        var user = m_currentUserProfileHelper.getCurrentUser();

        var userProfile = m_userProfileMapper.toUserProfile(request);
        userProfile.setUser(user);

        return m_userProfileMapper.toUserProfileDTO(m_userProfileRepository.save(userProfile));
    }

    public UserProfileDTO findUserProfileById(Long userProfileId)
    {
        var userProfile = m_userProfileRepository.findById(userProfileId)
                .orElseThrow(() -> new ApiException(MyError.USER_NOT_FOUND));

        return m_userProfileMapper.toUserProfileDTO(userProfile);
    }

    public UserProfileDTO findUserProfileByUsername(String username)
    {
        var user = m_currentUserProfileHelper.getUserByUsername(username);

        var userProfile = m_currentUserProfileHelper.getUserProfileByUser(user);

        return m_userProfileMapper.toUserProfileDTO(userProfile);
    }

    public UserProfileDTO findUserProfileByEmail(String email)
    {
        var user = m_currentUserProfileHelper.getUserByEmail((email));

        var userProfile = m_currentUserProfileHelper.getUserProfileByUser(user);

        return m_userProfileMapper.toUserProfileDTO(userProfile);
    }

    public UserProfileDTO updateUserProfile(UserProfileRequest request)
    {
        var userProfile = m_currentUserProfileHelper.getCurrentUserProfile();

        userProfile.setFullName(request.getFullName());
        userProfile.setPhone(request.getPhone());
        userProfile.setAddress(request.getAddress());
        userProfile.setGender(request.getGender());
        userProfile.setBirthDate(request.getBirthDate());
        userProfile.setUpdatedAt(LocalDateTime.now());

        return m_userProfileMapper.toUserProfileDTO(m_userProfileRepository.save(userProfile));
    }

    @Transactional
    public void deleteUserProfileById(Long id)
    {
        var userProfile = m_userProfileRepository.findById(id)
                .orElseThrow(() -> new ApiException(MyError.USER_PROFILE_NOT_FOUND));

        var user = userProfile.getUser();
        user.setProfile(null);

        m_userProfileRepository.deleteById(id);
    }

    @Transactional
    public void deleteUserProfile()
    {
        var userProfile = m_currentUserProfileHelper.getCurrentUserProfile();

        var user = userProfile.getUser();
        user.setProfile(null);

        m_userProfileRepository.delete(userProfile);
    }
}
