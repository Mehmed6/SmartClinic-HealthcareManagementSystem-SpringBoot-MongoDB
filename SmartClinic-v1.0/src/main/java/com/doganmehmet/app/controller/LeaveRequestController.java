package com.doganmehmet.app.controller;

import com.doganmehmet.app.dto.request.LeaveRequestSaveDTO;
import com.doganmehmet.app.dto.response.LeaveRequestDTO;
import com.doganmehmet.app.service.LeaveRequestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/leave-requests")
@RequiredArgsConstructor
public class LeaveRequestController {

    private final LeaveRequestService m_leaveRequestService;

    @PostMapping
    public ResponseEntity<LeaveRequestDTO> save(@Valid @RequestBody LeaveRequestSaveDTO request)
    {
        return ResponseEntity.ok(m_leaveRequestService.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<LeaveRequestDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(m_leaveRequestService.findLeaveRequestById(id));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<LeaveRequestDTO>> findByDoctorId(@PathVariable Long doctorId)
    {
        return ResponseEntity.ok(m_leaveRequestService.findLeaveRequestByDoctorId(doctorId));
    }

    @GetMapping("/status")
    public ResponseEntity<List<LeaveRequestDTO>> findByStatus(@RequestParam String status)
    {
        return ResponseEntity.ok(m_leaveRequestService.findLeaveRequestByStatus(status));
    }

    @GetMapping
    public ResponseEntity<List<LeaveRequestDTO>> findAll()
    {
        return ResponseEntity.ok(m_leaveRequestService.findAllLeaveRequests());
    }

    @PutMapping("approve/{id}")
    public ResponseEntity<LeaveRequestDTO> approveById(@PathVariable Long id)
    {
        return ResponseEntity.ok(m_leaveRequestService.approveLeaveRequestById(id));
    }

    @PutMapping("reject/{id}")
    public ResponseEntity<LeaveRequestDTO> rejectById(@PathVariable Long id)
    {
        return ResponseEntity.ok(m_leaveRequestService.rejectLeaveRequestById(id));
    }

    @PutMapping("update/{id}")
    public ResponseEntity<LeaveRequestDTO> update(@PathVariable Long id, @Valid @RequestBody LeaveRequestSaveDTO request)
    {
        return ResponseEntity.ok(m_leaveRequestService.updateLeaveRequest(id, request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id)
    {
        m_leaveRequestService.deleteLeaveRequestById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAll()
    {
        m_leaveRequestService.deleteAllLeaveRequests();
        return ResponseEntity.noContent().build();
    }
}
