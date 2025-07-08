package com.doganmehmet.app.controller;

import com.doganmehmet.app.dto.request.InvoiceRequest;
import com.doganmehmet.app.dto.response.InvoiceDTO;
import com.doganmehmet.app.service.InvoiceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/invoices")
@RequiredArgsConstructor
public class InvoiceController {
    private final InvoiceService m_invoiceService;

    @PostMapping
    public ResponseEntity<InvoiceDTO> save(@Valid @RequestBody InvoiceRequest request)
    {
        return ResponseEntity.ok(m_invoiceService.save(request));
    }

    @GetMapping({"/{invoiceId}"})
    public ResponseEntity<InvoiceDTO> findById(@PathVariable Long invoiceId)
    {
        return ResponseEntity.ok(m_invoiceService.findInvoiceById(invoiceId));
    }

    @GetMapping("/patient/{patientId}")
    public ResponseEntity<List<InvoiceDTO>> findAllByPatientId(@PathVariable Long patientId)
    {
        return ResponseEntity.ok(m_invoiceService.findAllInvoicesByPatientId(patientId));
    }

    @GetMapping("/issue-date")
    public ResponseEntity<List<InvoiceDTO>> findAllByIssuedDate(
            @RequestParam("date")
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate issuedDate)
    {
        return ResponseEntity.ok(m_invoiceService.findAllInvoicesByIssueDate(issuedDate));
    }

    @GetMapping("/status")
    public ResponseEntity<List<InvoiceDTO>> findAllByStatus(@RequestParam String status)
    {
        return ResponseEntity.ok(m_invoiceService.findAllInvoicesByStatus(status));
    }

    @GetMapping
    public ResponseEntity<List<InvoiceDTO>> findAll()
    {
        return ResponseEntity.ok(m_invoiceService.findAllInvoices());
    }

    @PutMapping("/{invoiceId}")
    public ResponseEntity<InvoiceDTO> update(@PathVariable Long invoiceId, @Valid @RequestBody InvoiceRequest request)
    {
        return ResponseEntity.ok(m_invoiceService.updateInvoice(invoiceId, request));
    }

    @DeleteMapping("/{invoiceId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Long invoiceId)
    {
        m_invoiceService.deleteInvoiceById(invoiceId);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteAll()
    {
        m_invoiceService.deleteAllInvoices();
        return ResponseEntity.noContent().build();
    }
}
