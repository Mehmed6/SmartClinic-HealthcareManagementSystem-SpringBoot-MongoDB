package com.doganmehmet.app.repository;

import com.doganmehmet.app.entity.Patient;
import com.doganmehmet.app.entity.UserProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IPatientRepository extends JpaRepository<Patient, Long> {

    Optional<Patient> findByUserProfile(UserProfile userProfile);
}
