package com.doganmehmet.app.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AppointmentRequest {
    @NotNull(message = "Doctor ID cannot be null!")
    private Long doctorId;
    @NotNull(message = "Appointment time cannot be null!")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime appointmentTime;
}
