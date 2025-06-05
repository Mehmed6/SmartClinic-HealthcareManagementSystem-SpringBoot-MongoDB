package com.doganmehmet.app.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalTime;

@Getter
@Setter
public class WorkScheduleRequest {
    @NotNull(message = "Doctor ID cannot be null")
    private Long doctorId;
    @NotNull(message = "Day of week cannot be null")
    private DayOfWeek dayOfWeek;
    @NotNull(message = "Start time cannot be null")
    private LocalTime startTime;
    @NotNull(message = "End time cannot be null")
    private LocalTime endTime;
}
