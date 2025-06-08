package com.doganmehmet.app.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class LaboratoryTestResultRequest {
    @NotNull(message = "Doctor ID cannot be null")
    private Long doctorId;
    @NotNull(message = "Patient ID cannot be null")
    private Long patientId;
    @NotNull(message = "Laboratory Test ID cannot be null")
    private Long laboratoryTestId;
    @NotNull(message = "Test result cannot be null")
    private Map<String, String> result;
}
