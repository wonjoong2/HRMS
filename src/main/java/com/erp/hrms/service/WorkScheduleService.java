package com.erp.hrms.service;

import com.erp.hrms.Entity.WorkSchedule;
import com.erp.hrms.Repository.WorkScheduleRepository;
import lombok.RequiredArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class WorkScheduleService {

    private final WorkScheduleRepository workScheduleRepository;

    public WorkSchedule WorkScheduleRegister(WorkSchedule workSchedule) {

        workSchedule.setUseYn("Y");
        workSchedule.setCreatedDt(LocalDateTime.now());
        return workScheduleRepository.save(workSchedule);

    }

    public List<WorkSchedule> WorkScheduleSearch(String name) {
        return workScheduleRepository.search(name);
    }

    public WorkSchedule update(Long id, WorkSchedule workSchedule) {
        WorkSchedule findWorkSchedule = workScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("스케줄 없음"));

        findWorkSchedule.setDeptName(workSchedule.getDeptName());
        findWorkSchedule.setDeptId(workSchedule.getDeptId());
        findWorkSchedule.setStartTime(workSchedule.getStartTime());
        findWorkSchedule.setEndTime(workSchedule.getEndTime());
        findWorkSchedule.setBreakMinutes(workSchedule.getBreakMinutes());

        return workScheduleRepository.save(findWorkSchedule);
    }

    public void delete(Long id) {
        WorkSchedule workSchedule = workScheduleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("스케줄 없음"));
        workSchedule.setUseYn("N");
        workSchedule.setModifiedDt(LocalDateTime.now());
        workScheduleRepository.save(workSchedule);
    }
}
