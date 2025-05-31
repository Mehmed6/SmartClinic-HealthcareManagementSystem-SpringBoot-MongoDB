package com.doganmehmet.app.service;

import com.doganmehmet.app.dto.request.DoctorRequest;
import com.doganmehmet.app.dto.response.DoctorDTO;
import com.doganmehmet.app.entity.UserProfile;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.helper.CurrentUserProfileHelper;
import com.doganmehmet.app.mapper.IDoctorMapper;
import com.doganmehmet.app.repository.IDoctorRepository;
import com.doganmehmet.app.repository.IUserProfileRepository;
import com.doganmehmet.app.repository.IUserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DoctorService {

    private final IDoctorRepository m_doctorRepository;
    private final IDoctorMapper m_doctorMapper;
    private final CurrentUserProfileHelper m_currentUserProfileHelper;

    public DoctorDTO save(DoctorRequest request)
    {
        var doctor = m_doctorMapper.toDoctor(request);
        var userProfile = m_currentUserProfileHelper.getCurrentUserProfile();
        doctor.setUserProfile(userProfile);

        return m_doctorMapper.toDoctorDTO(m_doctorRepository.save(doctor));
    }

    public DoctorDTO findDoctorById(Long id)
    {
        var doctor = m_doctorRepository.findById(id)
                .orElseThrow(() -> new ApiException(MyError.DOCTOR_NOT_FOUND));

        return m_doctorMapper.toDoctorDTO(doctor);
    }

    public DoctorDTO findDoctorByLicenceNumber(String licenceNumber)
    {
        var doctor = m_doctorRepository.findByLicenseNumber((licenceNumber))
                .orElseThrow(() -> new ApiException(MyError.DOCTOR_NOT_FOUND));

        return m_doctorMapper.toDoctorDTO(doctor);
    }

    public List<DoctorDTO> findAllDoctorsBySpeciality(String speciality)
    {
        return m_doctorMapper.toDoctorDTOs(m_doctorRepository.findAllBySpecialization(speciality));
    }

    public List<DoctorDTO> findAllDoctors()
    {
        return m_doctorMapper.toDoctorDTOs(m_doctorRepository.findAll());
    }

    public DoctorDTO updateDoctor(DoctorRequest request)
    {
        var doctor = m_currentUserProfileHelper.getCurrentUserProfile().getDoctor();
        doctor.setLicenseNumber(request.getLicenseNumber());
        doctor.setSpecialization(request.getSpecialization());

        return m_doctorMapper.toDoctorDTO(m_doctorRepository.save(doctor));
    }

    @Transactional
    public void deleteDoctorById(Long id)
    {
        var doctor = m_doctorRepository.findById(id)
                .orElseThrow(() -> new ApiException(MyError.DOCTOR_NOT_FOUND));

        m_currentUserProfileHelper.getCurrentUserProfile().setDoctor(null);
        m_doctorRepository.delete(doctor);
    }

    @Transactional
    public void deleteAllDoctors()
    {
        findAllDoctors().forEach(doctor -> doctor.setUserProfile(null));

        m_doctorRepository.deleteAll();
    }
}
