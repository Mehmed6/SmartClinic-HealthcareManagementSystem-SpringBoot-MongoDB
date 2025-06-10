package com.doganmehmet.app.repository;

import com.doganmehmet.app.entity.Appointment;
import com.doganmehmet.app.entity.Invoice;
import com.doganmehmet.app.entity.Patient;
import com.doganmehmet.app.enums.InvoiceStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface IInvoiceRepository extends JpaRepository<Invoice, Long> {
    List<Invoice> findAllByPatient(Patient patient);

    List<Invoice> findAllByIssuedDate(LocalDate issuedDate);

    List<Invoice> findAllByStatus(InvoiceStatus status);

    boolean existsByAppointment(Appointment appointment);
}
