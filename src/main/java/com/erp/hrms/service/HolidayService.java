package com.erp.hrms.service;

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
public class HolidayService {

    private final HolidayRepository holidayRepository;

    public List<Holiday> holidaySearch(String name, LocalDate holidayDate) {
        String nameParam = (name != null && !name.isBlank()) ? name : null;
        return holidayRepository.search(nameParam, holidayDate);
    }

    public Holiday holidayRegister(Holiday holiday) {

        holiday.setUseYn("Y");
        holiday.setCreatedDt(LocalDateTime.now());
        return holidayRepository.save(holiday);
    }


    public Holiday update(Long id, Holiday holiday) {

        Holiday findHoliday = holidayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("부서 없음"));

        findHoliday.setName(holiday.getName());
        findHoliday.setHolidayDate(holiday.getHolidayDate());

        return holidayRepository.save(findHoliday);
    }

    public void delete(Long id) {
        Holiday hol = holidayRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("공휴일 없음"));
        hol.setUseYn("N");
        holidayRepository.save(hol);
    }
}
