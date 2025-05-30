package com.doganmehmet.app.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientRequest {

    @NotBlank(message = "Insurance number cannot be blank")
    private String insuranceNo;
    @NotBlank(message = "Blood type cannot be blank")
    private String bloodType;
}
