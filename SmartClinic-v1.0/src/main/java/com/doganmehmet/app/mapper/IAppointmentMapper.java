package com.doganmehmet.app.mapper;

import com.doganmehmet.app.dto.request.AppointmentRequest;
import com.doganmehmet.app.dto.response.AppointmentDTO;
import com.doganmehmet.app.entity.Appointment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(implementationName = "AppointmentMapperImpl", componentModel = "spring")
public interface IAppointmentMapper {

    Appointment toAppointment(AppointmentRequest appointmentRequest);
    @Mapping(target = "patientName", source = "patient.userProfile.fullName")
    @Mapping(target = "doctorName", source = "doctor.userProfile.fullName")
    @Mapping(target = "appointmentTime", source = "appointmentTime")
    AppointmentDTO toAppointmentDTO(Appointment appointment);

    List<AppointmentDTO> toAppointmentDTOs(List<Appointment> appointments);
}
