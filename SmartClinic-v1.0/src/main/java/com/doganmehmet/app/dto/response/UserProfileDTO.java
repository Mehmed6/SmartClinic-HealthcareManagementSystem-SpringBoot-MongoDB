package com.doganmehmet.app.dto.response;

import com.doganmehmet.app.enums.Gender;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserProfileDTO {

    private String username;
    private String fullName;
    private String email;
    private String phone;
    private Gender gender;
    private String address;
    private LocalDate birthDate;
}
