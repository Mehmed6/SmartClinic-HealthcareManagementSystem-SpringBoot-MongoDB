package com.doganmehmet.app.mapper;

import com.doganmehmet.app.dto.request.PatientSaveBySecretaryRequest;
import com.doganmehmet.app.dto.request.UserProfileRequest;
import com.doganmehmet.app.dto.response.UserProfileDTO;
import com.doganmehmet.app.entity.UserProfile;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(implementationName = "UserProfileServiceMapperImpl", componentModel = "spring")
public interface IUserProfileMapper {

    UserProfile toUserProfile(UserProfileRequest request);

    @Mapping(target = "username", source = "user.username")
    @Mapping(target = "email", source = "user.email")
    UserProfileDTO toUserProfileDTO(UserProfile userProfile);

    UserProfile toUserProfile(PatientSaveBySecretaryRequest request);
}
