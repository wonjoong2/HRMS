package com.erp.hrms.service;

import com.erp.hrms.Entity.Attendance;
import com.erp.hrms.Entity.Dept;
import com.erp.hrms.Entity.Holiday;
import com.erp.hrms.Repository.AttendanceRepository;
import com.erp.hrms.Repository.HolidayRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
public class AttendanceService  {

    private final AttendanceRepository attendanceRepository;

    public Page<Attendance> search(String name, LocalDate workDate, Pageable pageable) {
        String nameParam = (name != null && !name.isBlank()) ? name : null;
        return attendanceRepository.search(nameParam, workDate, pageable);
    }
}
