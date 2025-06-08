package com.doganmehmet.app.repository;

import com.doganmehmet.app.entity.LaboratoryTestResult;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILaboratoryTestResultRepository extends MongoRepository<LaboratoryTestResult, String> {
    List<LaboratoryTestResult> findAllByPatientId(Long patientId);

    List<LaboratoryTestResult> findAllByDoctorId(Long doctorId);

    List<LaboratoryTestResult> findAllByLaboratoryTestId(Long laboratoryTestId);
}
