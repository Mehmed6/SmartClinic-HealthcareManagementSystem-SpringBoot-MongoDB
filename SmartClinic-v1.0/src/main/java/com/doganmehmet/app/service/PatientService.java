package com.doganmehmet.app.service;

import com.doganmehmet.app.audit.Auditable;
import com.doganmehmet.app.dto.request.PatientRequest;
import com.doganmehmet.app.dto.request.PatientSaveBySecretaryRequest;
import com.doganmehmet.app.dto.response.PatientDTO;
import com.doganmehmet.app.entity.User;
import com.doganmehmet.app.entity.UserProfile;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.helper.CurrentUserProfileHelper;
import com.doganmehmet.app.mapper.IPatientMapper;
import com.doganmehmet.app.repository.IPatientRepository;
import com.doganmehmet.app.repository.IUserProfileRepository;
import com.doganmehmet.app.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PatientService {

    private final IPatientRepository m_patientRepository;
    private final IPatientMapper m_patientMapper;
    private final CurrentUserProfileHelper m_currentUserProfileHelper;

    @Auditable(
            action = "Create Patient",
            entity = "Patient",
            description = "Patient created successfully"
    )
    public PatientDTO save(PatientRequest request)
    {
        var patient = m_patientMapper.toPatient(request);
        var userProfile = m_currentUserProfileHelper.getCurrentUserProfile();
        patient.setUserProfile(userProfile);

        return m_patientMapper.toPatientDTO(m_patientRepository.save(patient));
    }

    @Auditable(
            action = "Create Patient by Secretary",
            entity = "Patient",
            description = "Patient created successfully by secretary"
    )
    public PatientDTO savePatientBySecretary(PatientSaveBySecretaryRequest request)
    {
        var userProfile = m_currentUserProfileHelper.saveUserProfileBySecretary(request);
        var patient = m_patientMapper.toPAtient(request);
        patient.setUserProfile(userProfile);
        return m_patientMapper.toPatientDTO(m_patientRepository.save(patient));
    }

    @Auditable(
            action = "Find Patient by ID",
            entity = "Patient",
            description = "Patient found successfully"
    )
    public PatientDTO findPatientById(Long patientId)
    {
        return m_patientMapper.toPatientDTO(m_patientRepository.findById(patientId)
                .orElseThrow(() -> new ApiException(MyError.PATIENT_NOT_FOUND)));
    }

    @Auditable(
            action = "Find All Patients",
            entity = "Patient",
            description = "All patients found successfully"
    )
    public List<PatientDTO> findAllPatients()
    {
        return m_patientMapper.toPatientDTOs(m_patientRepository.findAll());
    }

    @Auditable(
            action = "Update Patient",
            entity = "Patient",
            description = "Patient updated successfully"
    )
    public PatientDTO updatePatient(PatientRequest request)
    {
        var patient = m_currentUserProfileHelper.getCurrentUserProfile().getPatient();
        patient.setBloodType(request.getBloodType());
        patient.setInsuranceNo(request.getInsuranceNo());

        return m_patientMapper.toPatientDTO(m_patientRepository.save(patient));
    }

    @Transactional
    @Auditable(
            action = "Delete Patient by ID",
            entity = "Patient",
            description = "Patient deleted successfully"
    )
    public void deletePatientById(Long id)
    {
        var patient = m_patientRepository.findById(id)
                .orElseThrow(() -> new ApiException(MyError.PATIENT_NOT_FOUND));

        m_currentUserProfileHelper.getCurrentUserProfile().setPatient(null);

       m_patientRepository.delete(patient);
    }
    @Transactional
    @Auditable(
            action = "Delete All Patients",
            entity = "Patient",
            description = "All patients deleted successfully"
    )
    public void deleteAllPatients()
    {
        findAllPatients().forEach(patient -> patient.setUserProfile(null));
        m_patientRepository.deleteAll();
    }
}
