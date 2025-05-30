package com.doganmehmet.app.mapper;

import com.doganmehmet.app.dto.request.PatientSaveBySecretaryRequest;
import com.doganmehmet.app.dto.request.RegisterRequest;
import com.doganmehmet.app.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(implementationName = "UserMapperImpl", componentModel = "spring")
public interface IUserMapper {

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser(RegisterRequest request);

    @Mapping(target = "password", ignore = true)
    @Mapping(target = "roles", ignore = true)
    User toUser(PatientSaveBySecretaryRequest request);
}
