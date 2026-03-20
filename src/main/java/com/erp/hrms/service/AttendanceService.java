package com.erp.hrms.service;

import com.erp.hrms.Entity.Attendance;
import com.erp.hrms.Entity.Dept;
import com.erp.hrms.Entity.Holiday;
import com.erp.hrms.Repository.AttendanceRepository;
import com.erp.hrms.Repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AttendanceService  {

    private final AttendanceRepository attendanceRepository;

    public List<Attendance> search(String name, LocalDate workDate) {
        String nameParam = (name != null && !name.isBlank()) ? name : null;
        return attendanceRepository.search(nameParam, workDate);
    }
}
