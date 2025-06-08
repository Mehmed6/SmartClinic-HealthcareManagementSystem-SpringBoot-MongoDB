package com.doganmehmet.app.service;

import com.doganmehmet.app.dto.request.LaboratoryTestResultRequest;
import com.doganmehmet.app.dto.response.LaboratoryTestDTO;
import com.doganmehmet.app.dto.response.LaboratoryTestResultDTO;
import com.doganmehmet.app.entity.LaboratoryTestResult;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.mapper.ILaboratoryTestResultMapper;
import com.doganmehmet.app.repository.IDoctorRepository;
import com.doganmehmet.app.repository.ILaboratoryTestRepository;
import com.doganmehmet.app.repository.ILaboratoryTestResultRepository;
import com.doganmehmet.app.repository.IPatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LaboratoryTestResultService {
    private final ILaboratoryTestResultRepository m_laboratoryTestResultRepository;
    private final ILaboratoryTestResultMapper m_laboratoryTestResultMapper;
    private final ILaboratoryTestRepository m_laboratoryTestRepository;
    private final IDoctorRepository m_doctorRepository;
    private final IPatientRepository m_patientRepository;

    private LaboratoryTestResultDTO convertToDTO(LaboratoryTestResult laboratoryTestResult)
    {
        var test = m_laboratoryTestRepository.findById(laboratoryTestResult.getLaboratoryTestId())
                .orElseThrow(() -> new ApiException(MyError.LABORATORY_TEST_NOT_FOUND));

        var dto = m_laboratoryTestResultMapper.toLaboratoryTestResultDTO(laboratoryTestResult);

        dto.setTestTypeName(test.getTestType().getName());
        dto.setStatus(test.getStatus().name());

        return dto;
    }

    private List<LaboratoryTestResultDTO> validateAndMapToDTO(List<LaboratoryTestResult> laboratoryTestResults)
    {
        if (laboratoryTestResults.isEmpty())
            throw new ApiException(MyError.LABORATORY_TEST_RESULT_NOT_FOUND);

        return laboratoryTestResults.stream()
                .map(this::convertToDTO)
                .toList();
    }

    @Transactional
    public LaboratoryTestResultDTO save(LaboratoryTestResultRequest request)
    {
        if (!m_doctorRepository.existsById(request.getDoctorId()))
            throw new ApiException(MyError.DOCTOR_NOT_FOUND);
        if (!m_patientRepository.existsById(request.getPatientId()))
            throw new ApiException(MyError.PATIENT_NOT_FOUND);
        if (!m_laboratoryTestRepository.existsById(request.getLaboratoryTestId()))
            throw new ApiException(MyError.LABORATORY_TEST_NOT_FOUND);

        var savedResult = m_laboratoryTestResultRepository.save(
                                            m_laboratoryTestResultMapper.toLaboratoryTestResult(request));

        return convertToDTO(savedResult);
    }

    public LaboratoryTestResultDTO findLaboratoryTestResultById(String id)
    {
        var result = m_laboratoryTestResultRepository.findById(id)
                .orElseThrow(() -> new ApiException(MyError.LABORATORY_TEST_RESULT_NOT_FOUND));

        return convertToDTO(result);
    }

    public List<LaboratoryTestResultDTO> findAllResultByDoctorId(Long doctorId)
    {
        if (!m_doctorRepository.existsById(doctorId))
            throw new ApiException(MyError.DOCTOR_NOT_FOUND);

        var results = m_laboratoryTestResultRepository.findAllByDoctorId(doctorId);

        return validateAndMapToDTO(results);
    }

    public List<LaboratoryTestResultDTO> findAllResultByPatientId(Long patientId)
    {
        if (!m_patientRepository.existsById(patientId))
            throw new ApiException(MyError.PATIENT_NOT_FOUND);

        var results = m_laboratoryTestResultRepository.findAllByPatientId(patientId);

        return validateAndMapToDTO(results);
    }

    public List<LaboratoryTestResultDTO> findAllResultByLaboratoryTestId(Long laboratoryTestId)
    {
        if (!m_laboratoryTestRepository.existsById(laboratoryTestId))
            throw new ApiException(MyError.LABORATORY_TEST_NOT_FOUND);

        var results = m_laboratoryTestResultRepository.findAllByLaboratoryTestId(laboratoryTestId);

        return validateAndMapToDTO(results);
    }

    public List<LaboratoryTestResultDTO> findAllLaboratoryTestResults()
    {
        var results = m_laboratoryTestResultRepository.findAll();

        return validateAndMapToDTO(results);
    }

    @Transactional
    public LaboratoryTestResultDTO updateLaboratoryTestResult(String laboratoryTestResultId, LaboratoryTestResultRequest request)
    {
        var result = m_laboratoryTestResultRepository.findById(laboratoryTestResultId)
                .orElseThrow(() -> new ApiException(MyError.LABORATORY_TEST_RESULT_NOT_FOUND));

        result.setDoctorId(request.getDoctorId());
        result.setPatientId(request.getPatientId());
        result.setLaboratoryTestId(request.getLaboratoryTestId());
        result.setResult(request.getResult());

        var savedResult = m_laboratoryTestResultRepository.save(result);
        return convertToDTO(savedResult);
    }

    @Transactional
    public void deleteLaboratoryTestResultById(String laboratoryTestResultId)
    {
        if (!m_laboratoryTestResultRepository.existsById(laboratoryTestResultId))
            throw new ApiException(MyError.LABORATORY_TEST_RESULT_NOT_FOUND);

        m_laboratoryTestResultRepository.deleteById(laboratoryTestResultId);
    }

    @Transactional
    public void deleteAllLaboratoryTestResults()
    {
        m_laboratoryTestResultRepository.deleteAll();
    }
}
