package com.doganmehmet.app.service;

import com.doganmehmet.app.audit.Auditable;
import com.doganmehmet.app.dto.request.LaboratoryTestRequest;
import com.doganmehmet.app.dto.response.LaboratoryTestDTO;
import com.doganmehmet.app.entity.LaboratoryTest;
import com.doganmehmet.app.enums.TestStatus;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.mapper.ILaboratoryTestMapper;
import com.doganmehmet.app.repository.ILaboratoryTestRepository;
import com.doganmehmet.app.repository.ITestTypeRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LaboratoryTestService {
    private final ILaboratoryTestRepository m_laboratoryTestRepository;
    private final ILaboratoryTestMapper m_laboratoryTestMapper;
    private final PatientService m_patientService;
    private final DoctorService m_doctorService;
    private final ITestTypeRepository m_testTypeRepository;

    private LaboratoryTestDTO convertToDTO(LaboratoryTest test)
    {
        var dto = m_laboratoryTestMapper.toLaboratoryTestDTO(test);
        var patientName = m_patientService.findPatientById(test.getPatientId()).getUserProfile().getFullName();
        var doctorName = m_doctorService.findDoctorById(test.getDoctorId()).getUserProfile().getFullName();

        dto.setPatientName(patientName);
        dto.setDoctorName(doctorName);

        return dto;
    }

    private List<LaboratoryTestDTO> validateAndMapToDTO(List<LaboratoryTest> laboratoryTests)
    {
        if (laboratoryTests.isEmpty())
            throw new ApiException(MyError.LABORATORY_TEST_NOT_FOUND);

        return laboratoryTests.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional
    @Auditable(
            action = "Create Laboratory Test",
            entity = "LaboratoryTest",
            description = "Laboratory test created successfully"
    )
    public LaboratoryTestDTO save(LaboratoryTestRequest request)
    {
        var laboratoryTest = m_laboratoryTestMapper.toLaboratoryTest(request);
        var testType = m_testTypeRepository.findByName(request.getTestType())
                .orElseThrow(() -> new ApiException(MyError.TEST_TYPE_NOT_FOUND, request.getTestType()));

        laboratoryTest.setTestType(testType);

        return convertToDTO(m_laboratoryTestRepository.save(laboratoryTest));
    }

    @Auditable(
            action = "Find Laboratory Test by ID",
            entity = "LaboratoryTest",
            description = "Laboratory test found successfully"
    )
    public LaboratoryTestDTO findLaboratoryTestById(Long laboratoryTestId)
    {
        var laboratoryTest = m_laboratoryTestRepository.findById(laboratoryTestId)
                .orElseThrow(() -> new ApiException(MyError.LABORATORY_TEST_NOT_FOUND));

        return convertToDTO(laboratoryTest);
    }

    @Auditable(
            action = "Find Laboratory Tests by Test Type Name",
            entity = "LaboratoryTest",
            description = "Laboratory tests found successfully by test type name"
    )
    public List<LaboratoryTestDTO> findAllLaboratoryTestsByTestTypeName(String testTypeName)
    {
        var laboratoryTests = m_laboratoryTestRepository.findAllByTestType_Name(testTypeName);

        return validateAndMapToDTO(laboratoryTests);
    }

    @Auditable(
            action = "Find Laboratory Tests by Patient ID",
            entity = "LaboratoryTest",
            description = "Laboratory tests found successfully by patient ID"
    )
    public List<LaboratoryTestDTO> findAllLaboratoryTestsByPatientId(Long patientId)
    {
        var laboratoryTests = m_laboratoryTestRepository.findAllByPatientId(patientId);

        return validateAndMapToDTO(laboratoryTests);
    }

    @Auditable(
            action = "Find Laboratory Tests by Doctor ID",
            entity = "LaboratoryTest",
            description = "Laboratory tests found successfully by doctor ID"
    )
    public List<LaboratoryTestDTO> findAllLaboratoryTestsByDoctorId(Long doctorId)
    {
        var laboratoryTests = m_laboratoryTestRepository.findAllByDoctorId(doctorId);

        return validateAndMapToDTO(laboratoryTests);
    }

@Auditable(
            action = "Find Laboratory Tests by Status",
            entity = "LaboratoryTest",
            description = "Laboratory tests found successfully by status"
    )
    public List<LaboratoryTestDTO> findAllLaboratoryTestsByStatus(String testStatus)
    {
        try {
            var status = TestStatus.valueOf(testStatus.toUpperCase());
            var laboratoryTests = m_laboratoryTestRepository.findAllByStatus(status);

            return validateAndMapToDTO(laboratoryTests);

        } catch (IllegalArgumentException e) {
            throw new ApiException(MyError.INVALID_TEST_STATUS, testStatus);
        }
    }

@Auditable(
            action = "Find All Laboratory Tests",
            entity = "LaboratoryTest",
            description = "All laboratory tests found successfully"
    )
    public List<LaboratoryTestDTO> findAllLaboratoryTests()
    {
        var laboratoryTests = m_laboratoryTestRepository.findAll();

        return validateAndMapToDTO(laboratoryTests);
    }

    @Auditable(
            action = "Complete Laboratory Test",
            entity = "LaboratoryTest",
            description = "Laboratory test completed successfully"
    )
    public LaboratoryTestDTO completeLaboratoryTest(Long laboratoryTestId)
    {
        var laboratoryTest = m_laboratoryTestRepository.findById(laboratoryTestId)
                .orElseThrow(() -> new ApiException(MyError.LABORATORY_TEST_NOT_FOUND));

        laboratoryTest.setStatus(TestStatus.COMPLETED);

        return convertToDTO(m_laboratoryTestRepository.save(laboratoryTest));
    }

    @Transactional
    @Auditable(
            action = "Update Laboratory Test",
            entity = "LaboratoryTest",
            description = "Laboratory test updated successfully"
    )
    public LaboratoryTestDTO updateLaboratoryTest(Long laboratoryTestId, LaboratoryTestRequest request)
    {
        var laboratoryTest = m_laboratoryTestRepository.findById(laboratoryTestId)
                .orElseThrow(() -> new ApiException(MyError.LABORATORY_TEST_NOT_FOUND));

        laboratoryTest.setPatientId(request.getPatientId());
        laboratoryTest.setDoctorId(request.getDoctorId());
        laboratoryTest.setTestType(m_testTypeRepository.findByName(request.getTestType())
                .orElseThrow(() -> new ApiException(MyError.TEST_TYPE_NOT_FOUND, request.getTestType())));

        return convertToDTO(m_laboratoryTestRepository.save(laboratoryTest));
    }

    @Transactional
    @Auditable(
            action = "Delete Laboratory Test by ID",
            entity = "LaboratoryTest",
            description = "Laboratory test deleted successfully"
    )
    public void deleteLaboratoryTestById(Long laboratoryTestId)
    {
        var laboratoryTest = m_laboratoryTestRepository.findById(laboratoryTestId)
                .orElseThrow(() -> new ApiException(MyError.LABORATORY_TEST_NOT_FOUND));

        m_laboratoryTestRepository.delete(laboratoryTest);
    }

    @Transactional
    @Auditable(
            action = "Delete All Laboratory Tests",
            entity = "LaboratoryTest",
            description = "All laboratory tests deleted successfully"
    )
    public void deleteAllLaboratoryTests()
    {
        m_laboratoryTestRepository.deleteAll();
    }
}
