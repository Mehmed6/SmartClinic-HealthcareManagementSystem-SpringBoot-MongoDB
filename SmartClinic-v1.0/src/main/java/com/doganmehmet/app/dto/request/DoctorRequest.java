package com.doganmehmet.app.dto.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class DoctorRequest {

    private String specialization;
    @NotBlank(message = "License number cannot be blank")
    private String licenseNumber;
}
