package com.doganmehmet.app.dto.response;

import com.doganmehmet.app.enums.InvoiceStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
public class InvoiceDTO {
    private Long patientId;
    private String patientName;
    private Long appointmentId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    private LocalDateTime appointmentDate;
    private BigDecimal amount;
    private LocalDate issuedDate;
    private InvoiceStatus status;
}
