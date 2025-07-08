package com.doganmehmet.app.controller;

import com.doganmehmet.app.dto.request.WorkScheduleRequest;
import com.doganmehmet.app.dto.response.WorkScheduleDTO;
import com.doganmehmet.app.service.WorkScheduleService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/work-schedules")
@RequiredArgsConstructor
public class WorkScheduleController {
    private final WorkScheduleService m_workScheduleService;

    @PostMapping
    public ResponseEntity<WorkScheduleDTO> save(@Valid @RequestBody WorkScheduleRequest request)
    {
        return ResponseEntity.ok(m_workScheduleService.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkScheduleDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(m_workScheduleService.findWorkScheduleById(id));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<WorkScheduleDTO>> findByDoctorId(@PathVariable Long doctorId)
    {
        return ResponseEntity.ok(m_workScheduleService.findAllWorkSchedulesByDoctorId(doctorId));
    }

    @GetMapping("/start-time")
    public ResponseEntity<List<WorkScheduleDTO>> findAllByStartTime(@RequestParam LocalTime startTime)
    {
        return ResponseEntity.ok(m_workScheduleService.findAllWorkSchedulesByStartTime(startTime));
    }
    @GetMapping("/end-time")
    public ResponseEntity<List<WorkScheduleDTO>> findAllByEndTime(@RequestParam LocalTime endTime)
    {
        return ResponseEntity.ok(m_workScheduleService.findAllWorkSchedulesByEndTime(endTime));
    }
    @GetMapping
    public ResponseEntity<List<WorkScheduleDTO>> findAll()
    {
        return ResponseEntity.ok(m_workScheduleService.findAllWorkSchedules());
    }
    @PutMapping("/{workScheduleId}")
    public ResponseEntity<WorkScheduleDTO> update(@PathVariable Long workScheduleId, @Valid @RequestBody WorkScheduleRequest request)
    {
        return ResponseEntity.ok(m_workScheduleService.updateWorkSchedule(workScheduleId, request));
    }
    @DeleteMapping("/{workScheduleId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long workScheduleId)
    {
        m_workScheduleService.deleteWorkScheduleById(workScheduleId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAll()
    {
        m_workScheduleService.deleteAllWorkSchedules();
        return ResponseEntity.noContent().build();
    }
}
