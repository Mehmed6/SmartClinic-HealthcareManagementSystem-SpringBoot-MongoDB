package com.doganmehmet.app.controller;

import com.doganmehmet.app.dto.request.LaboratoryTestRequest;
import com.doganmehmet.app.dto.response.LaboratoryTestDTO;
import com.doganmehmet.app.service.LaboratoryTestService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/laboratory-tests")
@RequiredArgsConstructor
public class LaboratoryTestController {
    private final LaboratoryTestService m_laboratoryTestService;

    @PostMapping
    public ResponseEntity<LaboratoryTestDTO> save(@Valid @RequestBody LaboratoryTestRequest request)
    {
        return ResponseEntity.ok(m_laboratoryTestService.save(request));
    }

    @GetMapping("/{laboratoryTestId}")
    public ResponseEntity<LaboratoryTestDTO> findById(@PathVariable Long laboratoryTestId)
    {
        return ResponseEntity.ok(m_laboratoryTestService.findLaboratoryTestById(laboratoryTestId));
    }

    @GetMapping("/by-test-type")
    public ResponseEntity<List<LaboratoryTestDTO>> findByTestTypeName(@RequestParam String name)
    {
        return ResponseEntity.ok(m_laboratoryTestService.findAllLaboratoryTestsByTestTypeName(name));
    }

    @GetMapping("/by-patient/{patientId}")
    public ResponseEntity<List<LaboratoryTestDTO>> findByPatientId(@PathVariable Long patientId)
    {
        return ResponseEntity.ok(m_laboratoryTestService.findAllLaboratoryTestsByPatientId(patientId));
    }

    @GetMapping("/by-doctor/{doctorId}")
    public ResponseEntity<List<LaboratoryTestDTO>> findByDoctorId(@PathVariable Long doctorId)
    {
        return ResponseEntity.ok(m_laboratoryTestService.findAllLaboratoryTestsByDoctorId(doctorId));
    }

    @GetMapping("/by-status/{status}")
    public ResponseEntity<List<LaboratoryTestDTO>> findByStatus(@PathVariable String status)
    {
        return ResponseEntity.ok(m_laboratoryTestService.findAllLaboratoryTestsByStatus(status));
    }

    @GetMapping
    public ResponseEntity<List<LaboratoryTestDTO>> findAll()
    {
        return ResponseEntity.ok(m_laboratoryTestService.findAllLaboratoryTests());
    }

    @PutMapping("/{laboratoryTestId}/complete")
    public ResponseEntity<LaboratoryTestDTO> complete(@PathVariable Long laboratoryTestId)
    {
        return ResponseEntity.ok(m_laboratoryTestService.completeLaboratoryTest(laboratoryTestId));
    }

    @PutMapping("/{laboratoryTestId}")
    public ResponseEntity<LaboratoryTestDTO> update(@PathVariable Long laboratoryTestId, @Valid @RequestBody LaboratoryTestRequest request)
    {
        return ResponseEntity.ok(m_laboratoryTestService.updateLaboratoryTest(laboratoryTestId, request));
    }

    @DeleteMapping("/{laboratoryTestId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long laboratoryTestId)
    {
        m_laboratoryTestService.deleteLaboratoryTestById(laboratoryTestId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll()
    {
        m_laboratoryTestService.deleteAllLaboratoryTests();
        return ResponseEntity.noContent().build();
    }
}
