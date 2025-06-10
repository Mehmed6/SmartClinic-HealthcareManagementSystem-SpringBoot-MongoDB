package com.doganmehmet.app.mapper;

import com.doganmehmet.app.dto.request.InvoiceRequest;
import com.doganmehmet.app.dto.response.InvoiceDTO;
import com.doganmehmet.app.entity.Invoice;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(implementationName = "InvoiceMapperImpl", componentModel = "spring")
public interface IInvoiceMapper {

    Invoice toInvoice(InvoiceRequest invoiceRequest);

    @Mapping(target = "patientId", source = "patient.id")
    @Mapping(target = "patientName", source = "patient.userProfile.fullName")
    @Mapping(target = "appointmentId", source = "appointment.id")
    @Mapping(target = "appointmentDate", source = "appointment.appointmentTime")
    InvoiceDTO toInvoiceDTO(Invoice invoice);

    List<InvoiceDTO> toInvoiceDTOs(List<Invoice> invoices);
}
