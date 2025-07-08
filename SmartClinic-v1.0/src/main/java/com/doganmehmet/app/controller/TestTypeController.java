package com.doganmehmet.app.controller;

import com.doganmehmet.app.dto.request.TestTypeRequest;
import com.doganmehmet.app.dto.response.TestTypeDTO;
import com.doganmehmet.app.service.TestTypeService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/test-types")
@RequiredArgsConstructor
public class TestTypeController {
    private final TestTypeService m_testTypeService;

    @PostMapping
    public ResponseEntity<TestTypeDTO> save(@Valid @RequestBody TestTypeRequest request)
    {
        return ResponseEntity.ok(m_testTypeService.save(request));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestTypeDTO> findById(@PathVariable Long id)
    {
        return ResponseEntity.ok(m_testTypeService.findTestTypeById(id));
    }

    @GetMapping("/name/{name}")
    public ResponseEntity<TestTypeDTO> findByName(@PathVariable String name)
    {
        return ResponseEntity.ok(m_testTypeService.findTestTypeByName(name));
    }

    @GetMapping
    public ResponseEntity<List<TestTypeDTO>> findAll()
    {
        return ResponseEntity.ok(m_testTypeService.findAllTestTypes());
    }

    @PutMapping("/{testTypeId}")
    public ResponseEntity<TestTypeDTO> update(@PathVariable Long testTypeId, @Valid @RequestBody TestTypeRequest request)
    {
        return ResponseEntity.ok(m_testTypeService.updateTestType(testTypeId, request));
    }

    @DeleteMapping("/{testTypeId}")
    public ResponseEntity<Void> deleteById(@PathVariable Long testTypeId)
    {
        m_testTypeService.deleteTestType(testTypeId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{name}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteByName(@PathVariable String name)
    {
        m_testTypeService.deleteTestTypeByName(name);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAll()
    {
        m_testTypeService.deleteAllTestTypes();
        return ResponseEntity.noContent().build();
    }
}
