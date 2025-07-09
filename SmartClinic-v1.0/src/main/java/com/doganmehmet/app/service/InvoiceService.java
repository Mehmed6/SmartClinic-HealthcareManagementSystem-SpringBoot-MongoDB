package com.doganmehmet.app.service;

import com.doganmehmet.app.audit.Auditable;
import com.doganmehmet.app.dto.request.InvoiceRequest;
import com.doganmehmet.app.dto.response.InvoiceDTO;
import com.doganmehmet.app.entity.Invoice;
import com.doganmehmet.app.enums.InvoiceStatus;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.mapper.IInvoiceMapper;
import com.doganmehmet.app.repository.IAppointmentRepository;
import com.doganmehmet.app.repository.IInvoiceRepository;
import com.doganmehmet.app.repository.IPatientRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class InvoiceService {
    private final IInvoiceRepository m_invoiceRepository;
    private final IInvoiceMapper m_invoiceMapper;
    private final IPatientRepository m_patientRepository;
    private final IAppointmentRepository m_appointmentRepository;

    private List<InvoiceDTO> mapInvoicesOrThrow(List<Invoice> invoices)
    {
        if (invoices.isEmpty())
            throw new ApiException(MyError.INVOICE_NOT_FOUND);

        return m_invoiceMapper.toInvoiceDTOs(invoices);
    }

    @Transactional
    @Auditable(
            action = "Create Invoice",
            entity = "Invoice",
            description = "Invoice created successfully"
    )
    public InvoiceDTO save(InvoiceRequest request)
    {
        var patient = m_patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ApiException(MyError.PATIENT_NOT_FOUND));
        var appointment = m_appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ApiException(MyError.APPOINTMENT_NOT_FOUND));

        if (m_invoiceRepository.existsByAppointment(appointment))
            throw new ApiException(MyError.INVOICE_ALREADY_EXISTS);

        var invoice = m_invoiceMapper.toInvoice(request);
        invoice.setPatient(patient);
        invoice.setAppointment(appointment);
        invoice.setStatus(InvoiceStatus.PENDING);

        return m_invoiceMapper.toInvoiceDTO(m_invoiceRepository.save(invoice));
    }

    @Auditable(
            action = "Find Invoice by ID",
            entity = "Invoice",
            description = "Invoice found successfully"
    )
    public InvoiceDTO findInvoiceById(Long id)
    {
        var invoice = m_invoiceRepository.findById(id)
                .orElseThrow(() -> new ApiException(MyError.INVOICE_NOT_FOUND));

        return m_invoiceMapper.toInvoiceDTO(invoice);
    }

    @Auditable(
            action = "Find Invoices by Patient ID",
            entity = "Invoice",
            description = "Invoices found successfully by patient ID"
    )
    public List<InvoiceDTO> findAllInvoicesByPatientId(Long patientId)
    {
        var patient = m_patientRepository.findById(patientId)
                .orElseThrow(() -> new ApiException(MyError.PATIENT_NOT_FOUND));

        return mapInvoicesOrThrow(m_invoiceRepository.findAllByPatient(patient));
    }

    @Auditable(
            action = "Find Invoices by Appointment ID",
            entity = "Invoice",
            description = "Invoices found successfully by appointment ID"
    )
    public List<InvoiceDTO> findAllInvoicesByIssueDate(LocalDate issueDate)
    {
        return mapInvoicesOrThrow(m_invoiceRepository.findAllByIssuedDate(issueDate));
    }

    @Auditable(
            action = "Find Invoices by Status",
            entity = "Invoice",
            description = "Invoices found successfully by status"
    )
    public List<InvoiceDTO> findAllInvoicesByStatus(String invoiceStatus)
    {
        try {
            var status = InvoiceStatus.valueOf(invoiceStatus.toUpperCase());
            return mapInvoicesOrThrow(m_invoiceRepository.findAllByStatus(status));
        }
        catch (IllegalArgumentException ignored) {
            throw new ApiException(MyError.INVALID_INVOICE_STATUS, invoiceStatus);
        }
    }

    @Auditable(
            action = "Find All Invoices",
            entity = "Invoice",
            description = "All invoices found successfully"
    )
    public List<InvoiceDTO> findAllInvoices()
    {
        return mapInvoicesOrThrow(m_invoiceRepository.findAll());
    }

    @Transactional
    @Auditable(
            action = "Update Invoice",
            entity = "Invoice",
            description = "Invoice updated successfully"
    )
    public InvoiceDTO updateInvoice(Long invoiceId, InvoiceRequest request)
    {
        var invoice = m_invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ApiException(MyError.INVOICE_NOT_FOUND));

        var patient = m_patientRepository.findById(request.getPatientId())
                .orElseThrow(() -> new ApiException(MyError.PATIENT_NOT_FOUND));

        var appointment = m_appointmentRepository.findById(request.getAppointmentId())
                .orElseThrow(() -> new ApiException(MyError.APPOINTMENT_NOT_FOUND));

        if (!Objects.equals(invoice.getAppointment().getId(), request.getAppointmentId())
            && !m_invoiceRepository.existsByAppointment(appointment))
            invoice.setAppointment(appointment);

        invoice.setPatient(patient);
        invoice.setAmount(request.getAmount());
        invoice.setIssuedDate(request.getIssuedDate());

        return m_invoiceMapper.toInvoiceDTO(m_invoiceRepository.save(invoice));
    }
    @Transactional
@Auditable(
            action = "Delete Invoice by ID",
            entity = "Invoice",
            description = "Invoice deleted successfully"
    )
    public void deleteInvoiceById(Long invoiceId)
    {
        if (!m_invoiceRepository.existsById(invoiceId))
            throw new ApiException(MyError.INVOICE_NOT_FOUND);

        m_invoiceRepository.deleteById(invoiceId);
    }
    @Transactional
@Auditable(
            action = "Delete All Invoices",
            entity = "Invoice",
            description = "All invoices deleted successfully"
    )
    public void deleteAllInvoices()
    {
        m_invoiceRepository.deleteAll();
    }
}
