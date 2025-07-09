package com.doganmehmet.app.service;

import com.doganmehmet.app.audit.Auditable;
import com.doganmehmet.app.dto.request.UserProfileRequest;
import com.doganmehmet.app.dto.response.UserProfileDTO;
import com.doganmehmet.app.entity.UserProfile;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.helper.CurrentUserProfileHelper;
import com.doganmehmet.app.mapper.IUserProfileMapper;
import com.doganmehmet.app.repository.IUserProfileRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserProfileService {
    private final IUserProfileRepository m_userProfileRepository;
    private final IUserProfileMapper m_userProfileMapper;
    private final CurrentUserProfileHelper m_currentUserProfileHelper;

    @Auditable(
            action = "Create User Profile",
            entity = "UserProfile",
            description = "User profile created successfully"
    )
    public UserProfileDTO save(UserProfileRequest request)
    {
        var user = m_currentUserProfileHelper.getCurrentUser();

        var userProfile = m_userProfileMapper.toUserProfile(request);
        userProfile.setUser(user);

        return m_userProfileMapper.toUserProfileDTO(m_userProfileRepository.save(userProfile));
    }

    @Auditable(
            action = "Find User Profile by ID",
            entity = "UserProfile",
            description = "User profile found successfully"
    )
    public UserProfileDTO findUserProfileById(Long userProfileId)
    {
        var userProfile = m_userProfileRepository.findById(userProfileId)
                .orElseThrow(() -> new ApiException(MyError.USER_NOT_FOUND));

        return m_userProfileMapper.toUserProfileDTO(userProfile);
    }

    @Auditable(
            action = "Find User Profile by Username",
            entity = "UserProfile",
            description = "User profile found successfully by username"
    )
    public UserProfileDTO findUserProfileByUsername(String username)
    {
        var user = m_currentUserProfileHelper.getUserByUsername(username);

        var userProfile = m_currentUserProfileHelper.getUserProfileByUser(user);

        return m_userProfileMapper.toUserProfileDTO(userProfile);
    }

    @Auditable(
            action = "Find User Profile by Email",
            entity = "UserProfile",
            description = "User profile found successfully by email"
    )
    public UserProfileDTO findUserProfileByEmail(String email)
    {
        var user = m_currentUserProfileHelper.getUserByEmail((email));

        var userProfile = m_currentUserProfileHelper.getUserProfileByUser(user);

        return m_userProfileMapper.toUserProfileDTO(userProfile);
    }

    @Auditable(
            action = "Update User Profile",
            entity = "UserProfile",
            description = "User profile updated successfully"
    )
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
    @Auditable(
            action = "Delete User Profile by ID",
            entity = "UserProfile",
            description = "User profile deleted successfully by ID"
    )
    public void deleteUserProfileById(Long id)
    {
        var userProfile = m_userProfileRepository.findById(id)
                .orElseThrow(() -> new ApiException(MyError.USER_PROFILE_NOT_FOUND));

        var user = userProfile.getUser();
        user.setProfile(null);

        m_userProfileRepository.deleteById(id);
    }

    @Transactional
    @Auditable(
            action = "Delete Current User Profile",
            entity = "UserProfile",
            description = "Current user profile deleted successfully"
    )
    public void deleteUserProfile()
    {
        var userProfile = m_currentUserProfileHelper.getCurrentUserProfile();

        var user = userProfile.getUser();
        user.setProfile(null);

        m_userProfileRepository.delete(userProfile);
    }

    @Transactional
    @Auditable(
            action = "Delete All User Profiles",
            entity = "UserProfile",
            description = "All user profiles deleted successfully"
    )
    public void deleteAll()
    {
        m_userProfileRepository.deleteAll();
    }
}
