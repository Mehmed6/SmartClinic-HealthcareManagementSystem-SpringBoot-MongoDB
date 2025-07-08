package com.doganmehmet.app.controller;

import com.doganmehmet.app.dto.request.DoctorRequest;
import com.doganmehmet.app.dto.response.DoctorDTO;
import com.doganmehmet.app.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService m_doctorService;

    @PostMapping
    public ResponseEntity<DoctorDTO> save(@Valid @RequestBody DoctorRequest request)
    {
        return ResponseEntity.ok(m_doctorService.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(m_doctorService.findDoctorById(id));
    }

    @GetMapping("/license/{licenseNumber}")
    public ResponseEntity<DoctorDTO> findByLicenseNumber(@PathVariable String licenseNumber)
    {
        return ResponseEntity.ok(m_doctorService.findDoctorByLicenceNumber(licenseNumber));
    }

    @GetMapping("/speciality")
    public ResponseEntity<List<DoctorDTO>> findAllBySpeciality(@RequestParam String speciality)
    {
        return ResponseEntity.ok(m_doctorService.findAllDoctorsBySpeciality(speciality));
    }

    @GetMapping
    public ResponseEntity<List<DoctorDTO>> findAll()
    {
        return ResponseEntity.ok(m_doctorService.findAllDoctors());
    }

    @PutMapping("update")
    public ResponseEntity<DoctorDTO> update(@Valid @RequestBody DoctorRequest request)
    {
        return ResponseEntity.ok(m_doctorService.updateDoctor(request));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteById(@PathVariable Long id)
    {
        m_doctorService.deleteDoctorById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAll()
    {
        m_doctorService.deleteAllDoctors();
        return ResponseEntity.noContent().build();
    }
}
