package com.doganmehmet.app.repository;

import com.doganmehmet.app.entity.Doctor;
import com.doganmehmet.app.entity.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalTime;
import java.util.List;

@Repository
public interface IWorkScheduleRepository extends JpaRepository<WorkSchedule, Long> {
    List<WorkSchedule> findAllByDoctor(Doctor doctor);

    List<WorkSchedule> findAllByStartTime(LocalTime startTime);

    List<WorkSchedule> findAllByEndTime(LocalTime endTime);
}
