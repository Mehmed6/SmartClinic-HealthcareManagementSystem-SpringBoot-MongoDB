package com.doganmehmet.app.dto.request;

import com.doganmehmet.app.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class PatientSaveBySecretaryRequest {
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "Full name cannot be blank")
    private String fullName;
    @NotBlank(message = "Email cannot be blank")
    private String email;
    @NotBlank(message = "Phone number cannot be blank")
    private String phone;
    @NotNull(message = "Gender cannot be blank")
    private Gender gender;
    private String address;
    @NotNull(message = "Birth date cannot be blank")
    private LocalDate birthDate;
    @NotBlank(message = "Insurance number cannot be blank")
    private String insuranceNo;
    @NotBlank(message = "Blood type cannot be blank")
    private String bloodType;
}
