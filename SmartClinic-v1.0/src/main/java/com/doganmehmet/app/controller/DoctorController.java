package com.doganmehmet.app.controller;

import com.doganmehmet.app.dto.request.DoctorRequest;
import com.doganmehmet.app.dto.response.DoctorDTO;
import com.doganmehmet.app.service.DoctorService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/doctors")
@RequiredArgsConstructor
public class DoctorController {

    private final DoctorService m_doctorService;

    @PostMapping("save")
    public ResponseEntity<DoctorDTO> save(@Valid @RequestBody DoctorRequest request)
    {
        return ResponseEntity.ok(m_doctorService.save(request));
    }

    @GetMapping("find/id/{id}")
    public ResponseEntity<DoctorDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(m_doctorService.findDoctorById(id));
    }

    @GetMapping("find/license/{licenseNumber}")
    public ResponseEntity<DoctorDTO> findByLicenseNumber(@PathVariable String licenseNumber)
    {
        return ResponseEntity.ok(m_doctorService.findDoctorByLicenceNumber(licenseNumber));
    }

    @GetMapping("findAll/speciality")
    public ResponseEntity<List<DoctorDTO>> findAllBySpeciality(@RequestParam String speciality)
    {
        return ResponseEntity.ok(m_doctorService.findAllDoctorsBySpeciality(speciality));
    }

    @GetMapping("findAll")
    public ResponseEntity<List<DoctorDTO>> findAll()
    {
        return ResponseEntity.ok(m_doctorService.findAllDoctors());
    }

    @PutMapping("update")
    public ResponseEntity<DoctorDTO> update(@Valid @RequestBody DoctorRequest request)
    {
        return ResponseEntity.ok(m_doctorService.updateDoctor(request));
    }

    @DeleteMapping("delete/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id)
    {
        m_doctorService.deleteDoctorById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("deleteAll")
    public ResponseEntity<Void> deleteAll()
    {
        m_doctorService.deleteAllDoctors();
        return ResponseEntity.noContent().build();
    }
}
