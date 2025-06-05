package com.doganmehmet.app.service;

import com.doganmehmet.app.dto.request.WorkScheduleRequest;
import com.doganmehmet.app.dto.response.WorkScheduleDTO;
import com.doganmehmet.app.exception.ApiException;
import com.doganmehmet.app.exception.MyError;
import com.doganmehmet.app.mapper.IWorkScheduleMapper;
import com.doganmehmet.app.repository.IDoctorRepository;
import com.doganmehmet.app.repository.IWorkScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkScheduleService {
    private final IWorkScheduleRepository m_workScheduleRepository;
    private final IWorkScheduleMapper m_workScheduleMapper;
    private final IDoctorRepository m_doctorRepository;

    @Transactional
    public WorkScheduleDTO save(WorkScheduleRequest request)
    {
        var doctor = m_doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ApiException(MyError.DOCTOR_NOT_FOUND));
        var workSchedule = m_workScheduleMapper.toWorkSchedule(request);
        workSchedule.setDoctor(doctor);

        try {
            return m_workScheduleMapper.toWorkScheduleDTO(m_workScheduleRepository.save(workSchedule));
        }
        catch (Exception ignored) {
            throw new ApiException(MyError.WORK_SCHEDULE_ALREADY_EXISTS);
        }

    }

    public WorkScheduleDTO findWorkScheduleById(Long id)
    {
        var workSchedule = m_workScheduleRepository.findById(id)
                .orElseThrow(() -> new ApiException(MyError.WORK_SCHEDULE_NOT_FOUND));

        return m_workScheduleMapper.toWorkScheduleDTO(workSchedule);
    }

    public List<WorkScheduleDTO> findAllWorkSchedulesByDoctorId(Long doctorId)
    {
       var doctor = m_doctorRepository.findById(doctorId)
                .orElseThrow(() -> new ApiException(MyError.DOCTOR_NOT_FOUND));

       var workSchedules = m_workScheduleRepository.findAllByDoctor(doctor);

         if (workSchedules.isEmpty())
              throw new ApiException(MyError.WORK_SCHEDULE_NOT_FOUND);

         return m_workScheduleMapper.toWorkScheduleDTOs(workSchedules);
    }

    public List<WorkScheduleDTO> findAllWorkSchedulesByStartTime(LocalTime startTime)
    {
        var workSchedules = m_workScheduleRepository.findAllByStartTime(startTime);

        if (workSchedules.isEmpty())
            throw new ApiException(MyError.WORK_SCHEDULE_NOT_FOUND);

        return m_workScheduleMapper.toWorkScheduleDTOs(workSchedules);
    }

    public List<WorkScheduleDTO> findAllWorkSchedulesByEndTime(LocalTime endTime)
    {
        var workSchedules = m_workScheduleRepository.findAllByEndTime(endTime);

        if (workSchedules.isEmpty())
            throw new ApiException(MyError.WORK_SCHEDULE_NOT_FOUND);

        return m_workScheduleMapper.toWorkScheduleDTOs(workSchedules);
    }

    public List<WorkScheduleDTO> findAllWorkSchedules()
    {
        var workSchedules = m_workScheduleRepository.findAll();

        if (workSchedules.isEmpty())
            throw new ApiException(MyError.WORK_SCHEDULE_NOT_FOUND);

        return m_workScheduleMapper.toWorkScheduleDTOs(workSchedules);
    }

    @Transactional
    public WorkScheduleDTO updateWorkSchedule(Long id, WorkScheduleRequest request)
    {
        var workSchedule = m_workScheduleRepository.findById(id)
                .orElseThrow(() -> new ApiException(MyError.WORK_SCHEDULE_NOT_FOUND));

        var doctor = m_doctorRepository.findById(request.getDoctorId())
                .orElseThrow(() -> new ApiException(MyError.DOCTOR_NOT_FOUND));
        workSchedule.setDoctor(doctor);
        workSchedule.setDayOfWeek(request.getDayOfWeek());
        workSchedule.setStartTime(request.getStartTime());
        workSchedule.setEndTime(request.getEndTime());

        try {
            return m_workScheduleMapper.toWorkScheduleDTO(m_workScheduleRepository.save(workSchedule));
        } catch (Exception ignored) {
            throw new ApiException(MyError.WORK_SCHEDULE_ALREADY_EXISTS);
        }
    }

    @Transactional
    public void deleteWorkScheduleById(Long id)
    {
        var workSchedule = m_workScheduleRepository.findById(id)
                .orElseThrow(() -> new ApiException(MyError.WORK_SCHEDULE_NOT_FOUND));

        m_workScheduleRepository.delete(workSchedule);
    }

     @Transactional
    public void deleteAllWorkSchedules()
    {
        m_workScheduleRepository.deleteAll();
    }
}
