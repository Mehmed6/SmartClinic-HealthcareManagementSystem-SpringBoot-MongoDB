package com.doganmehmet.app.entity;

import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.Map;
@Getter
@Setter
@Document(collection = "laboratory_test_results")
public class LaboratoryTestResult {

    @Id
    private String laboratoryTestResultId;
    private Long doctorId;
    private Long patientId;
    private Long laboratoryTestId;
    private Map<String, String> result;
    private LocalDateTime createdAt = LocalDateTime.now();

}
