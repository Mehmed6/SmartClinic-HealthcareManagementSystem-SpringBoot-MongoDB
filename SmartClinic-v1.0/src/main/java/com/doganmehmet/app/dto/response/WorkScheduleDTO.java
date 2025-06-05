package com.doganmehmet.app.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalTime;

@Getter
@Setter
public class WorkScheduleDTO {
    private DoctorDTO doctor;
    private String dayOfWeek;
    private LocalTime startTime;
    private LocalTime endTime;
}
