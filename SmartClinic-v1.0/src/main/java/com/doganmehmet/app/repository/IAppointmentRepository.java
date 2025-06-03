package com.doganmehmet.app.repository;

import com.doganmehmet.app.entity.Appointment;
import com.doganmehmet.app.entity.Doctor;
import com.doganmehmet.app.entity.Patient;
import com.doganmehmet.app.enums.AppointmentStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IAppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findAllByPatient(Patient patient);

    List<Appointment> findAllByDoctor(Doctor doctor);

    List<Appointment> findAllByStatus(AppointmentStatus status);

    void deleteAllByPatient(Patient patient);

    void deleteAllByDoctor(Doctor doctor);
}
