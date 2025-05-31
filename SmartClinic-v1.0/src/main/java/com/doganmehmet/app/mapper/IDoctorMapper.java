package com.doganmehmet.app.mapper;

import com.doganmehmet.app.dto.request.DoctorRequest;
import com.doganmehmet.app.dto.response.DoctorDTO;
import com.doganmehmet.app.entity.Doctor;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(implementationName = "DoctorMapperImpl", componentModel = "spring", uses = IUserProfileMapper.class)
public interface IDoctorMapper {

    Doctor toDoctor(DoctorRequest request);

    DoctorDTO toDoctorDTO(Doctor doctor);
    List<DoctorDTO> toDoctorDTOs(List<Doctor> doctors);
}
