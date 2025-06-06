package com.doganmehmet.app.repository;

import com.doganmehmet.app.entity.LaboratoryTest;
import com.doganmehmet.app.entity.TestType;
import com.doganmehmet.app.enums.TestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ILaboratoryTestRepository extends JpaRepository<LaboratoryTest, Long> {

    List<LaboratoryTest> findAllByTestType_Name(String testTypeName);

    List<LaboratoryTest> findAllByPatientId(Long patientId);

    List<LaboratoryTest> findAllByDoctorId(Long doctorId);

    List<LaboratoryTest> findAllByStatus(TestStatus status);
}
