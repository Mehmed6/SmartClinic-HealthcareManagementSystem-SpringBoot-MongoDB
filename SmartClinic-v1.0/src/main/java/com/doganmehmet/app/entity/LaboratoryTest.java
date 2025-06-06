package com.doganmehmet.app.entity;

import com.doganmehmet.app.enums.TestStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "laboratory_tests")
public class LaboratoryTest extends BaseEntity{

    private Long patientId;
    private Long doctorId;

    @Enumerated(EnumType.STRING)
    private TestStatus status = TestStatus.PENDING;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "test_type_id", nullable = false)
    private TestType testType;

}
