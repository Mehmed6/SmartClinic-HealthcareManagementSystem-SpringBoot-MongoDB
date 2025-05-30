package com.doganmehmet.app.mapper;

import com.doganmehmet.app.dto.request.PatientRequest;
import com.doganmehmet.app.dto.request.PatientSaveBySecretaryRequest;
import com.doganmehmet.app.dto.response.PatientDTO;
import com.doganmehmet.app.entity.Patient;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(implementationName = "PatientMapperImpl", componentModel = "spring", uses = IUserProfileMapper.class)
public interface IPatientMapper {

    @Mapping(target = "userProfile", ignore = true)
    Patient toPatient(PatientRequest patientRequest);
    PatientDTO toPatientDTO(Patient patient);

    List<PatientDTO> toPatientDTOs(List<Patient> patientList);

    Patient toPAtient(PatientSaveBySecretaryRequest request);
}
