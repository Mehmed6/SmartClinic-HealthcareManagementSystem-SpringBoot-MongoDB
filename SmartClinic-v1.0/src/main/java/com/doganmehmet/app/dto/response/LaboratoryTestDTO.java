package com.doganmehmet.app.dto.response;

import com.doganmehmet.app.enums.TestStatus;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LaboratoryTestDTO {

    private String patientName;
    private String doctorName;
    private String testTypeName;
    private TestStatus status;

}
