package com.doganmehmet.app.dto.response;

import com.doganmehmet.app.enums.AppointmentStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentDTO {
    private String patientName;
    private String doctorName;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime appointmentTime;
    private AppointmentStatus status;
}
