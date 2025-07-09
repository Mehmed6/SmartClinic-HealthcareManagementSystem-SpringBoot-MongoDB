package com.doganmehmet.app.service;

import com.doganmehmet.app.audit.Auditable;
import com.doganmehmet.app.dto.request.AppointmentRequest;
import com.doganmehmet.app.dto.response.AppointmentDTO;
import com.doganmehmet.app.entity.Appointment;
import com.doganmehmet.app.entity.Doctor;
import com.doganmehmet.app.entity.Patient;
import com.doganmehmet.app.enums.AppointmentStatus;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.helper.CurrentUserProfileHelper;
import com.doganmehmet.app.mapper.IAppointmentMapper;
import com.doganmehmet.app.repository.IAppointmentRepository;
import com.doganmehmet.app.repository.IDoctorRepository;
import com.doganmehmet.app.repository.IPatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AppointmentService {

    private final IAppointmentRepository m_appointmentRepository;
    private final IAppointmentMapper m_appointmentMapper;
    private final IDoctorRepository m_doctorRepository;
    private final IPatientRepository m_patientRepository;
    private final CurrentUserProfileHelper m_currentUserProfileHelper;

    private Doctor findDoctorOrThrow(Long doctorId)
    {
        return m_doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ApiException(MyError.DOCTOR_NOT_FOUND));
    }
    private Patient findPatientOrThrow()
    {
        return m_patientRepository.findByUserProfile(m_currentUserProfileHelper.getCurrentUserProfile())
                .orElseThrow(() -> new ApiException(MyError.PATIENT_NOT_FOUND));
    }
    private Appointment findAppointmentOrThrow(Long appointmentId)
    {
        return m_appointmentRepository.findById(appointmentId)
                .orElseThrow(() -> new ApiException(MyError.APPOINTMENT_NOT_FOUND));
    }

    @Auditable(
            action = "Create Appointment",
            entity = "Appointment",
            description = "Appointment created successfully"
    )
    public AppointmentDTO save(AppointmentRequest request)
    {
        var appointment = m_appointmentMapper.toAppointment(request);

        var doctor = findDoctorOrThrow(request.getDoctorId());
        var patient = findPatientOrThrow();

        appointment.setDoctor(doctor);
        appointment.setPatient(patient);
        appointment.setAppointmentTime(request.getAppointmentTime());

        return m_appointmentMapper.toAppointmentDTO(m_appointmentRepository.save(appointment));
    }

    @Auditable(
            action = "Find Appointment by ID",
            entity = "Appointment",
            description = "Appointment found successfully"
    )
    public AppointmentDTO findAppointmentById(Long appointmentId)
    {
        var appointment = findAppointmentOrThrow(appointmentId);

        return m_appointmentMapper.toAppointmentDTO(appointment);
    }

    @Auditable(
            action = "Find Appointments by Patient ID",
            entity = "Appointment",
            description = "Appointments found successfully by patient ID"
    )
    public List<AppointmentDTO> findAllAppointmentsByPatientId(Long patientId)
    {
        var patient = m_patientRepository.findById(patientId)
                .orElseThrow(() -> new ApiException(MyError.PATIENT_NOT_FOUND));

        return m_appointmentMapper.toAppointmentDTOs(
                m_appointmentRepository.findAllByPatient((patient)));
    }

    @Auditable(
            action = "Find Appointments by Doctor ID",
            entity = "Appointment",
            description = "Appointments found successfully by doctor ID"
    )
    public List<AppointmentDTO> findAllAppointmentsByDoctorId(Long doctorId)
    {
        var doctor = findDoctorOrThrow(doctorId);

        return m_appointmentMapper.toAppointmentDTOs(
                m_appointmentRepository.findAllByDoctor(doctor));
    }

    @Auditable(
            action = "Find Appointments by Status",
            entity = "Appointment",
            description = "Appointments found successfully by status"
    )
    public List<AppointmentDTO> findAllAppointmentsByStatus(String status)
    {
        try {
            var appointmentStatus = AppointmentStatus.valueOf(status.toUpperCase());
            return m_appointmentMapper.toAppointmentDTOs(
                    m_appointmentRepository.findAllByStatus((appointmentStatus)));
        }
        catch (IllegalArgumentException e) {
            throw new ApiException(MyError.INVALID_APPOINTMENT_STATUS, status);
        }
    }

    @Auditable(
            action = "Find All Appointments",
            entity = "Appointment",
            description = "All appointments found successfully"
    )
    public List<AppointmentDTO> findAllAppointments()
    {
        return m_appointmentMapper.toAppointmentDTOs(
                m_appointmentRepository.findAll());
    }

    @Auditable(
            action = "Complete Appointment",
            entity = "Appointment",
            description = "Appointment completed successfully"
    )
    public AppointmentDTO completeAppointment(Long appointmentId)
    {
        var appointment = findAppointmentOrThrow(appointmentId);

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED)
            throw new ApiException(MyError.APPOINTMENT_ALREADY_COMPLETED_OR_CANCELLED, appointment.getStatus());

        appointment.setStatus(AppointmentStatus.COMPLETED);

        return m_appointmentMapper.toAppointmentDTO( m_appointmentRepository.save(appointment));
    }

    @Auditable(
            action = "Cancel Appointment",
            entity = "Appointment",
            description = "Appointment cancelled successfully"
    )
    public AppointmentDTO cancelAppointment(Long appointmentId)
    {
        var appointment = findAppointmentOrThrow(appointmentId);

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED)
            throw new ApiException(MyError.APPOINTMENT_ALREADY_COMPLETED_OR_CANCELLED, appointment.getStatus());

        appointment.setStatus(AppointmentStatus.CANCELLED);
        return m_appointmentMapper.toAppointmentDTO(m_appointmentRepository.save(appointment));
    }

    @Auditable(
            action = "Update Appointment",
            entity = "Appointment",
            description = "Appointment updated successfully"
    )
    public AppointmentDTO updateAppointment(Long appointmentId, AppointmentRequest request)
    {
        var appointment = findAppointmentOrThrow(appointmentId);

        if (appointment.getStatus() != AppointmentStatus.SCHEDULED)
            throw new ApiException(MyError.APPOINTMENT_ALREADY_COMPLETED_OR_CANCELLED, appointment.getStatus());

        appointment.setDoctor(findDoctorOrThrow(request.getDoctorId()));
        appointment.setAppointmentTime(request.getAppointmentTime());

        return m_appointmentMapper.toAppointmentDTO(m_appointmentRepository.save(appointment));
    }

    @Transactional
    @Auditable(
            action = "Delete Appointment by ID",
            entity = "Appointment",
            description = "Appointment deleted successfully by ID"
    )
    public void deleteAppointmentById(Long appointmentId)
    {
        m_appointmentRepository.delete(findAppointmentOrThrow(appointmentId));
    }

    @Transactional
    @Auditable(
            action = "Delete All Appointments by Patient ID",
            entity = "Appointment",
            description = "All appointments deleted successfully by patient ID"
    )
    public void deleteAllAppointmentsByPatientId(Long patientId)
    {
        var patient = m_patientRepository.findById(patientId)
                .orElseThrow(() -> new ApiException(MyError.PATIENT_NOT_FOUND));

        m_appointmentRepository.deleteAllByPatient(patient);
    }

    @Transactional
    @Auditable(
            action = "Delete All Appointments by Doctor ID",
            entity = "Appointment",
            description = "All appointments deleted successfully by doctor ID"
    )
    public void deleteAllAppointmentsByDoctorId(Long doctorId)
    {
        m_appointmentRepository.deleteAllByDoctor(findDoctorOrThrow(doctorId));
    }

    @Transactional
    @Auditable(
            action = "Delete All Appointments",
            entity = "Appointment",
            description = "All appointments deleted successfully"
    )
    public void deleteAllAppointments()
    {
        m_appointmentRepository.deleteAll();
    }
}
