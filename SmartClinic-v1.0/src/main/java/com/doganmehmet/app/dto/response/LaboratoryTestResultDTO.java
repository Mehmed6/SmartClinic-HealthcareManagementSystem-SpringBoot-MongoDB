package com.doganmehmet.app.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class LaboratoryTestResultDTO {

    private Long doctorId;
    private Long patientId;
    private Long laboratoryTestId;
    private Map<String, String> result;
    private String createdAt;
    private String testTypeName;
    private String status;
}
