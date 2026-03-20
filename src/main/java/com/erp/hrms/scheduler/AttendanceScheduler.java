package com.erp.hrms.scheduler;

import com.erp.hrms.Entity.Attendance;
import com.erp.hrms.Entity.User;
import com.erp.hrms.Entity.WorkSchedule;
import com.erp.hrms.Repository.AttendanceRepository;
import com.erp.hrms.Repository.HolidayRepository;
import com.erp.hrms.Repository.UserRepository;
import com.erp.hrms.Repository.WorkScheduleRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class AttendanceScheduler {

    private final UserRepository userRepository;
    private final WorkScheduleRepository workScheduleRepository;
    private final HolidayRepository holidayRepository;
    private final AttendanceRepository attendanceRepository;

    @Scheduled(cron = "0 10 09 * * *") // 매일 새벽 1시 실행
//    @Scheduled(cron = "0 42 17 * * *") // 매일 오전 9시 30분 실행
    @Transactional
    public void createDailyAttendance() {

        System.out.println("===== 스케줄러 실행 =====");
        System.out.println("===== 스케줄러 실행 =====");
        System.out.println("===== 스케줄러 실행 =====");
        System.out.println("===== 스케줄러 실행 =====");
        System.out.println("===== 스케줄러 실행 =====");

        Random random = new Random();

        LocalDate workDate = LocalDate.now();

        // 주말 제외
        DayOfWeek day = workDate.getDayOfWeek();
        if(day == DayOfWeek.SATURDAY || day == DayOfWeek.SUNDAY){
            return;
        }

        // 공휴일 제외
        boolean isHoliday = holidayRepository.existsByHolidayDate(workDate);
        if(isHoliday){
            return;
        }

        List<User> users = userRepository.findByRole("ROLE_USER");

        List<Attendance> list = new ArrayList<>();

        for(User user : users){

            String deptId = user.getDept().getDeptId();

            WorkSchedule schedule =
                    workScheduleRepository.findByDeptId(deptId)
                            .orElseThrow();

            LocalTime baseStart = schedule.getStartTime();
            LocalTime baseEnd = schedule.getEndTime();
            int breakMinutes = schedule.getBreakMinutes();

            // 결근 확률
            if(random.nextInt(100) < 5){
                continue;
            }

            int rand = random.nextInt(100);
            LocalTime checkIn;

            if(rand < 93){
                int offset = random.nextInt(6) - 3;
                checkIn = baseStart.plusMinutes(offset);
            }else{
                int offset = random.nextInt(40) + 5;
                checkIn = baseStart.plusMinutes(offset);
            }

            int randOut = random.nextInt(100);
            int checkOutOffset;

            if(randOut < 80){
                checkOutOffset = random.nextInt(20) - 10;
            }else{
                checkOutOffset = random.nextInt(120);
            }

            LocalTime checkOut = baseEnd.plusMinutes(checkOutOffset);

            int workMinutes =
                    (int) Duration.between(checkIn, checkOut).toMinutes()
                            - breakMinutes;

            if(workMinutes < 0){
                workMinutes = 0;
            }

            int lateMinutes =
                    Math.max((int)Duration.between(baseStart,checkIn).toMinutes(),0);

            int earlyLeaveMinutes =
                    Math.max((int)Duration.between(checkOut,baseEnd).toMinutes(),0);

            int overtimeMinutes =
                    Math.max((int)Duration.between(baseEnd,checkOut).toMinutes(),0);

            String status = "정상";

            if(lateMinutes > 0 && earlyLeaveMinutes > 0){
                status = "지각+조퇴";
            }else if(lateMinutes > 0){
                status = "지각";
            }else if(earlyLeaveMinutes > 0){
                status = "조퇴";
            }

            Attendance a = new Attendance();

            a.setUser(user);
            a.setWork_date(workDate);
            a.setCheck_in_time(checkIn);
            a.setCheck_out_time(checkOut);
            a.setWork_minutes(workMinutes);
            a.setLate_minutes(lateMinutes);
            a.setEarly_leave_minutes(earlyLeaveMinutes);
            a.setOvertime_minutes(overtimeMinutes);
            a.setAttendance_status(status);
            a.setCreated_dt(LocalDateTime.now());

            list.add(a);
        }
        System.out.println("저장할 데이터 수 : " + list.size());

        attendanceRepository.saveAll(list);
    }
}
