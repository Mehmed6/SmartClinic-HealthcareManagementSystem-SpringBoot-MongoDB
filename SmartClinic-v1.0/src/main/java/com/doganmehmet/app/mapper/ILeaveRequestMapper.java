package com.doganmehmet.app.mapper;

import com.doganmehmet.app.dto.request.LeaveRequestSaveDTO;
import com.doganmehmet.app.dto.response.LeaveRequestDTO;
import com.doganmehmet.app.entity.LeaveRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(implementationName = "LeaveRequestMapperImpl", componentModel = "spring")
public interface ILeaveRequestMapper {

    LeaveRequest toLeaveRequest(LeaveRequestSaveDTO leaveRequest);

    @Mapping(target = "doctorName", source = "doctor.userProfile.fullName")
    LeaveRequestDTO toLeaveRequestDTO(LeaveRequest leaveRequest);
    List<LeaveRequestDTO> toLeaveRequestDTOs(List<LeaveRequest> leaveRequests);
}
