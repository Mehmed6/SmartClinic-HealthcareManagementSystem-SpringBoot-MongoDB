package com.doganmehmet.app.mapper;

import com.doganmehmet.app.dto.request.WorkScheduleRequest;
import com.doganmehmet.app.dto.response.WorkScheduleDTO;
import com.doganmehmet.app.entity.WorkSchedule;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(implementationName = "WorkScheduleMapperImpl", componentModel = "spring", uses = IDoctorMapper.class)
public interface IWorkScheduleMapper {

    WorkSchedule toWorkSchedule(WorkScheduleRequest workScheduleRequest);

    WorkScheduleDTO toWorkScheduleDTO(WorkSchedule workSchedule);
    List<WorkScheduleDTO> toWorkScheduleDTOs(List<WorkSchedule> workSchedules);
}
