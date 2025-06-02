package com.doganmehmet.app.dto.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class LeaveRequestDTO {
    private String doctorName;
    private String reason;
    private LocalDate startDate;
    private LocalDate endDate;
    private String status;
}
