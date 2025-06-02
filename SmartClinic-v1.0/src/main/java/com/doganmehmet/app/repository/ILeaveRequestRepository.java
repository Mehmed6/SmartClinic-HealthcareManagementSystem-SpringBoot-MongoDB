package com.doganmehmet.app.repository;

import com.doganmehmet.app.entity.LeaveRequest;
import com.doganmehmet.app.enums.LeaveStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ILeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {
    List<LeaveRequest> findByDoctor_Id(Long doctorId);

    List<LeaveRequest> findByStatus(LeaveStatus status);
}
