package com.doganmehmet.app.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DoctorDTO {

    private String specialization;
    private String licenseNumber;
    private UserProfileDTO userProfile;

}
