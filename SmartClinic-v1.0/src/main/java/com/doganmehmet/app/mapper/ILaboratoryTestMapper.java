package com.doganmehmet.app.mapper;

import com.doganmehmet.app.dto.request.LaboratoryTestRequest;
import com.doganmehmet.app.dto.response.LaboratoryTestDTO;
import com.doganmehmet.app.entity.LaboratoryTest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(implementationName = "LaboratoryTestMapperImpl", componentModel = "spring")
public interface ILaboratoryTestMapper {
    @Mapping(target = "testType", ignore = true)
    LaboratoryTest toLaboratoryTest(LaboratoryTestRequest laboratoryTestRequest);

    @Mapping(target = "testTypeName", source = "testType.name")
    LaboratoryTestDTO toLaboratoryTestDTO(LaboratoryTest laboratoryTest);
}

