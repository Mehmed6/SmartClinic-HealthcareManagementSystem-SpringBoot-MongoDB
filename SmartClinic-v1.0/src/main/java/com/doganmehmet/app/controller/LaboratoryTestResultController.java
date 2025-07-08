package com.doganmehmet.app.controller;

import com.doganmehmet.app.dto.request.LaboratoryTestResultRequest;
import com.doganmehmet.app.dto.response.LaboratoryTestResultDTO;
import com.doganmehmet.app.service.LaboratoryTestResultService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/laboratory-test-results")
@RequiredArgsConstructor
public class LaboratoryTestResultController {
    private final LaboratoryTestResultService m_laboratoryTestResultService;

    @PostMapping
    public ResponseEntity<LaboratoryTestResultDTO> save(@Valid @RequestBody LaboratoryTestResultRequest request)
    {
        return ResponseEntity.ok(m_laboratoryTestResultService.save(request));
    }

    @GetMapping("{laboratoryTestResultId}")
    public ResponseEntity<LaboratoryTestResultDTO> findById(@PathVariable String laboratoryTestResultId)
    {
        return ResponseEntity.ok(m_laboratoryTestResultService.findLaboratoryTestResultById(laboratoryTestResultId));
    }

    @GetMapping("/by-patient/{patientId}")
    public ResponseEntity<List<LaboratoryTestResultDTO>> findByPatientId(@PathVariable Long patientId)
    {
        return ResponseEntity.ok(m_laboratoryTestResultService.findAllResultByPatientId(patientId));
    }

    @GetMapping("/by-doctor/{doctorId}")
    public ResponseEntity<List<LaboratoryTestResultDTO>> findByDoctorId(@PathVariable Long doctorId)
    {
        return ResponseEntity.ok(m_laboratoryTestResultService.findAllResultByDoctorId(doctorId));
    }

    @GetMapping("/by-laboratory-test/{laboratoryTestId}")
    public ResponseEntity<List<LaboratoryTestResultDTO>> findByLaboratoryTestId(@PathVariable Long laboratoryTestId)
    {
        return ResponseEntity.ok(m_laboratoryTestResultService.findAllResultByLaboratoryTestId(laboratoryTestId));
    }

    @GetMapping
    public ResponseEntity<List<LaboratoryTestResultDTO>> findAll()
    {
        return ResponseEntity.ok(m_laboratoryTestResultService.findAllLaboratoryTestResults());
    }

    @PutMapping("/{laboratoryTestResultId}")
    public ResponseEntity<LaboratoryTestResultDTO> update(@PathVariable String laboratoryTestResultId, @Valid @RequestBody LaboratoryTestResultRequest request)
    {
        return ResponseEntity.ok(m_laboratoryTestResultService.updateLaboratoryTestResult(laboratoryTestResultId, request));
    }

    @DeleteMapping("/{laboratoryTestResultId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable String laboratoryTestResultId)
    {
        m_laboratoryTestResultService.deleteLaboratoryTestResultById(laboratoryTestResultId);
        return ResponseEntity.noContent().build();
    }
    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAll()
    {
        m_laboratoryTestResultService.deleteAllLaboratoryTestResults();
        return ResponseEntity.noContent().build();
    }
}
