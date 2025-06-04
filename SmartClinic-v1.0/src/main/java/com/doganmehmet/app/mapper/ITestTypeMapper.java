package com.doganmehmet.app.mapper;

import com.doganmehmet.app.dto.request.TestTypeRequest;
import com.doganmehmet.app.dto.response.TestTypeDTO;
import com.doganmehmet.app.entity.TestType;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(implementationName = "TestTypeMapperImpl", componentModel = "spring")
public interface ITestTypeMapper {

    @Mapping(target = "name", source = "testTypeName")
    TestType toTestType(TestTypeRequest testTypeRequest);

    @Mapping(target = "testTypeName", source = "name")
    TestTypeDTO toTestTypeDTO(TestType testType);

    List<TestTypeDTO> toTestTypeDTOs(List<TestType> testTypes);
}
