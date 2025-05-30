package com.doganmehmet.app.dto.request;

import com.doganmehmet.app.enums.Gender;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserProfileRequest {
    @NotBlank(message = "Full name cannot be blank")
    private String fullName;
    @NotBlank(message = "Phone cannot be blank")
    private String phone;
    @Enumerated(EnumType.STRING)
    private Gender gender;
    private String address;
    @NotNull(message = "Birth date cannot be blank")
    private LocalDate birthDate;
}
