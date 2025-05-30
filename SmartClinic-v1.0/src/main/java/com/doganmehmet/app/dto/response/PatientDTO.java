package com.doganmehmet.app.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatientDTO {
    private String insuranceNo;
    private String bloodType;
    private UserProfileDTO userProfile;
}
