package com.doganmehmet.app.controller;

import com.doganmehmet.app.dto.request.AppointmentRequest;
import com.doganmehmet.app.dto.response.AppointmentDTO;
import com.doganmehmet.app.service.AppointmentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/appointments")
@RequiredArgsConstructor
public class AppointmentController {
    private final AppointmentService m_appointmentService;

    @PostMapping
    public ResponseEntity<AppointmentDTO> save(@Valid @RequestBody AppointmentRequest request)
    {
        return ResponseEntity.ok(m_appointmentService.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AppointmentDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(m_appointmentService.findAppointmentById(id));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<AppointmentDTO>> findAllByPatientId(@PathVariable Long patientId)
    {
        return ResponseEntity.ok(m_appointmentService.findAllAppointmentsByPatientId(patientId));
    }

    @GetMapping("/doctor/{doctorId}")
    public ResponseEntity<List<AppointmentDTO>> findAllByDoctorId(@PathVariable Long doctorId)
    {
        return ResponseEntity.ok(m_appointmentService.findAllAppointmentsByDoctorId(doctorId));
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<List<AppointmentDTO>> findAllByStatus(@PathVariable String status)
    {
        return ResponseEntity.ok(m_appointmentService.findAllAppointmentsByStatus(status));
    }

    @GetMapping
    public ResponseEntity<List<AppointmentDTO>> findAll()
    {
        return ResponseEntity.ok(m_appointmentService.findAllAppointments());
    }

    @PutMapping("/{appointmentId}")
    public ResponseEntity<AppointmentDTO> update(@PathVariable Long appointmentId,@Valid @RequestBody AppointmentRequest request)
    {
        return ResponseEntity.ok(m_appointmentService.updateAppointment(appointmentId, request));
    }

    @PutMapping("/{appointmentId}/complete")
    public ResponseEntity<AppointmentDTO> complete(@PathVariable Long appointmentId)
    {
        return ResponseEntity.ok(m_appointmentService.completeAppointment(appointmentId));
    }

    @PutMapping("/{appointmentId}/cancel")
    public ResponseEntity<AppointmentDTO> cancel(@PathVariable Long appointmentId)
    {
        return ResponseEntity.ok(m_appointmentService.cancelAppointment(appointmentId));
    }

    @DeleteMapping("/{appointmentId}")
    public ResponseEntity<Void> delete(@PathVariable Long appointmentId)
    {
        m_appointmentService.deleteAppointmentById(appointmentId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/patient/{patientId}")
    public ResponseEntity<Void> deleteAllByPatientId(@PathVariable Long patientId)
    {
        m_appointmentService.deleteAllAppointmentsByPatientId(patientId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping("/doctor/{doctorId}")
    public ResponseEntity<Void> deleteAllByDoctorId(@PathVariable Long doctorId)
    {
        m_appointmentService.deleteAllAppointmentsByDoctorId(doctorId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteAll()
    {
        m_appointmentService.deleteAllAppointments();
        return ResponseEntity.noContent().build();
    }
}
