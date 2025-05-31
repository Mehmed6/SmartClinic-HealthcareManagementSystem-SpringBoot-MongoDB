package com.doganmehmet.app.repository;

import com.doganmehmet.app.entity.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IDoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByLicenseNumber(String licenseNumber);

    List<Doctor> findAllBySpecialization(String specialization);
}
