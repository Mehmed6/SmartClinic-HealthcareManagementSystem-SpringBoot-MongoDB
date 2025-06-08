package com.doganmehmet.app.mapper;

import com.doganmehmet.app.dto.request.LaboratoryTestResultRequest;
import com.doganmehmet.app.dto.response.LaboratoryTestResultDTO;
import com.doganmehmet.app.entity.LaboratoryTestResult;
import org.mapstruct.Mapper;

@Mapper(implementationName = "LaboratoryTestResultMapperImpl", componentModel = "spring")
public interface ILaboratoryTestResultMapper {

    LaboratoryTestResult toLaboratoryTestResult(LaboratoryTestResultRequest laboratoryTestResultRequest);
    LaboratoryTestResultDTO toLaboratoryTestResultDTO(LaboratoryTestResult laboratoryTestResult);
}
