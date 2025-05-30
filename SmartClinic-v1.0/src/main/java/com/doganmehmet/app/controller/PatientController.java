package com.doganmehmet.app.controller;

import com.doganmehmet.app.dto.request.PatientRequest;
import com.doganmehmet.app.dto.request.PatientSaveBySecretaryRequest;
import com.doganmehmet.app.dto.response.PatientDTO;
import com.doganmehmet.app.service.PatientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/patients")
@RequiredArgsConstructor
public class PatientController {

    private final PatientService m_patientService;

    @PostMapping("save")
    public ResponseEntity<PatientDTO> save(@Valid @RequestBody PatientRequest request)
    {
        return ResponseEntity.ok(m_patientService.save(request));
    }

    @PostMapping("by-secretary")
    public ResponseEntity<PatientDTO> savePatientBySecretary(@Valid @RequestBody PatientSaveBySecretaryRequest request)
    {
        return ResponseEntity.ok(m_patientService.savePatientBySecretary(request));
    }

    @GetMapping("/find/id/{id}")
    public ResponseEntity<PatientDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(m_patientService.findPatientById(id));
    }

    @GetMapping("/findAll")
    public ResponseEntity<List<PatientDTO>> findAll()
    {
        return ResponseEntity.ok(m_patientService.findAllPatients());
    }

    @PutMapping("/update")
    public ResponseEntity<PatientDTO> update(@Valid @RequestBody PatientRequest request)
    {
        return ResponseEntity.ok(m_patientService.updatePatient(request));
    }

    @DeleteMapping("/delete/id/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id)
    {
        m_patientService.deletePatientById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/deleteAll")
    public ResponseEntity<Void> deleteAll()
    {
        m_patientService.deleteAllPatients();
        return ResponseEntity.noContent().build();
    }
}
