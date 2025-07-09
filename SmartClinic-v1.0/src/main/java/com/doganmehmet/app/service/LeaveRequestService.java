package com.doganmehmet.app.service;

import com.doganmehmet.app.audit.Auditable;
import com.doganmehmet.app.dto.request.LeaveRequestSaveDTO;
import com.doganmehmet.app.dto.response.LeaveRequestDTO;
import com.doganmehmet.app.entity.LeaveRequest;
import com.doganmehmet.app.enums.LeaveStatus;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.helper.CurrentUserProfileHelper;
import com.doganmehmet.app.mapper.ILeaveRequestMapper;
import com.doganmehmet.app.repository.ILeaveRequestRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class LeaveRequestService {

    private final ILeaveRequestRepository m_leaveRequestRepository;
    private final ILeaveRequestMapper m_leaveRequestMapper;
    private final CurrentUserProfileHelper m_currentUserProfileHelper;

    private boolean isRequestOwner(LeaveRequest request)
    {
        var currentId = m_currentUserProfileHelper.getCurrentUserProfile().getDoctor().getId();
        var requestOwnerId = request.getDoctor().getId();

        return Objects.equals(currentId, requestOwnerId);
    }

    @Auditable(
            action = "Create Leave Request",
            entity = "LeaveRequest",
            description = "Leave request created successfully"
    )
    public LeaveRequestDTO save(LeaveRequestSaveDTO leaveRequestSaveDTO)
    {
        var leaveRequest = m_leaveRequestMapper.toLeaveRequest(leaveRequestSaveDTO);
        leaveRequest.setDoctor(m_currentUserProfileHelper.getCurrentUserProfile().getDoctor());

        return m_leaveRequestMapper.toLeaveRequestDTO(m_leaveRequestRepository.save(leaveRequest));
    }

    @Auditable(
            action = "Find Leave Request by ID",
            entity = "LeaveRequest",
            description = "Leave request found successfully"
    )
    public LeaveRequestDTO findLeaveRequestById(Long id)
    {
        var leaveRequest = m_leaveRequestRepository.findById(id)
                .orElseThrow(() -> new ApiException(MyError.LEAVE_REQUEST_NOT_FOUND));

        return m_leaveRequestMapper.toLeaveRequestDTO(leaveRequest);
    }

    @Auditable(
            action = "Find Leave Requests by Doctor ID",
            entity = "LeaveRequest",
            description = "Leave requests found successfully by doctor ID"
    )
    public List<LeaveRequestDTO> findLeaveRequestByDoctorId(Long doctorId)
    {
        return m_leaveRequestMapper.toLeaveRequestDTOs(m_leaveRequestRepository.findByDoctor_Id(doctorId));
    }

    @Auditable(
            action = "Find Leave Requests by Status",
            entity = "LeaveRequest",
            description = "Leave requests found successfully by status"
    )
    public List<LeaveRequestDTO> findLeaveRequestByStatus(String leaveStatus)
    {
        try {
            var status = LeaveStatus.valueOf(leaveStatus.toUpperCase(Locale.ENGLISH));
            return m_leaveRequestMapper.toLeaveRequestDTOs(m_leaveRequestRepository.findByStatus((status)));
        }
        catch (Exception ignored) {
            throw new ApiException(MyError.INVALID_LEAVE_STATUS, leaveStatus);
        }
    }

    @Auditable(
            action = "Find All Leave Requests",
            entity = "LeaveRequest",
            description = "All leave requests found successfully"
    )
    public List<LeaveRequestDTO> findAllLeaveRequests()
    {
        return m_leaveRequestMapper.toLeaveRequestDTOs(m_leaveRequestRepository.findAll());
    }

    @Auditable(
            action = "Approve Leave Request by ID",
            entity = "LeaveRequest",
            description = "Leave request approved successfully"
    )
    public LeaveRequestDTO approveLeaveRequestById(Long leaveRequestId)
    {
        var leaveRequest = m_leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new ApiException(MyError.LEAVE_REQUEST_NOT_FOUND));

        if (!isRequestOwner(leaveRequest))
            throw new ApiException(MyError.UNAUTHORIZED_ACTION);

        leaveRequest.setStatus(LeaveStatus.APPROVED);
        return m_leaveRequestMapper.toLeaveRequestDTO(m_leaveRequestRepository.save(leaveRequest));
    }

    @Auditable(
            action = "Reject Leave Request by ID",
            entity = "LeaveRequest",
            description = "Leave request rejected successfully"
    )
    public LeaveRequestDTO rejectLeaveRequestById(Long leaveRequestId)
    {
        var leaveRequest = m_leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new ApiException(MyError.LEAVE_REQUEST_NOT_FOUND));

        if (!isRequestOwner(leaveRequest))
            throw new ApiException(MyError.UNAUTHORIZED_ACTION);

        leaveRequest.setStatus(LeaveStatus.REJECTED);
        return m_leaveRequestMapper.toLeaveRequestDTO(m_leaveRequestRepository.save(leaveRequest));
    }

    @Auditable(
            action = "Update Leave Request",
            entity = "LeaveRequest",
            description = "Leave request updated successfully"
    )
    public LeaveRequestDTO updateLeaveRequest(Long leaveRequestId, LeaveRequestSaveDTO leaveRequestSaveDTO)
    {
        var leaveRequest = m_leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new ApiException(MyError.LEAVE_REQUEST_NOT_FOUND));

        leaveRequest.setReason(leaveRequestSaveDTO.getReason());
        leaveRequest.setStartDate(leaveRequestSaveDTO.getStartDate());
        leaveRequest.setEndDate(leaveRequestSaveDTO.getEndDate());

        return m_leaveRequestMapper.toLeaveRequestDTO(m_leaveRequestRepository.save(leaveRequest));
    }

    @Transactional
    @Auditable(
            action = "Delete Leave Request by ID",
            entity = "LeaveRequest",
            description = "Leave request deleted successfully"
    )
    public void deleteLeaveRequestById(Long leaveRequestId)
    {
        var leaveRequest = m_leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new ApiException(MyError.LEAVE_REQUEST_NOT_FOUND));

        if (isRequestOwner(leaveRequest))
            m_leaveRequestRepository.delete(leaveRequest);
        else
            throw new ApiException(MyError.UNAUTHORIZED_ACTION);
    }

    @Transactional
    @Auditable(
            action = "Delete All Leave Requests",
            entity = "LeaveRequest",
            description = "All leave requests deleted successfully"
    )
    public void deleteAllLeaveRequests()
    {
        m_leaveRequestRepository.deleteAll();
    }


}
