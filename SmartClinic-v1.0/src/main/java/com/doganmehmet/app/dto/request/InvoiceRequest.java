package com.doganmehmet.app.dto.request;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class InvoiceRequest {
    private Long patientId;
    private Long appointmentId;
    private BigDecimal amount;
    private LocalDate issuedDate;
}
