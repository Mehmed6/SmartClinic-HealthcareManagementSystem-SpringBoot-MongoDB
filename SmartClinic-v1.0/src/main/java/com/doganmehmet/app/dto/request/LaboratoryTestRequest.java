package com.doganmehmet.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaboratoryTestRequest {

    @NotNull(message = "Patient ID cannot be null")
    private Long patientId;
    @NotNull(message = "Doctor ID cannot be null")
    private Long doctorId;
    @NotBlank(message = "Test type cannot be blank")
    private String testType;
}
