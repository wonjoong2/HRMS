package com.erp.hrms.controller;

import com.erp.hrms.Repository.AttendanceRepository;
import com.erp.hrms.Repository.UserRepository;
import com.erp.hrms.service.CustomUserDetails;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class IndexController {

    private final UserRepository userRepository;
    private final AttendanceRepository attendanceRepository;


    /**
     * @Method Name : Index
     * @작성일 : 2026. 3. 11.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 인덱스 view 페이지 이동
     * @param
     * @return
     */
    @RequestMapping("/")
    public String Index(Model model, HttpSession session,
                        @AuthenticationPrincipal CustomUserDetails userDetails){

        session.setAttribute("deptId", userDetails.getDeptId());
        session.setAttribute("name", userDetails.getName());
        session.setAttribute("email",userDetails.getUsername());

        LocalDate today = LocalDate.now();
        LocalDateTime now = LocalDateTime.now();

        System.out.println("============================today");
        System.out.println(today);
        System.out.println(now);

        model.addAttribute("totalUser", userRepository.countByRole("ROLE_USER"));
        model.addAttribute("attendCount", attendanceRepository.countAttendToday(today));
        model.addAttribute("lateCount", attendanceRepository.countLateToday(today));
        model.addAttribute("absentCount", attendanceRepository.countAbsentToday(today));

        return "index";
    }

    /**
     * @Method Name : monthlyAttendance
     * @작성일 : 2026. 3. 11.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 대시보드 용 월별 근태 통계 조회 
     * @param
     * @return
     */
    @GetMapping("/api/dashboard/monthly-attendance")
    @ResponseBody
    public List<Object[]> monthlyAttendance(){
        return attendanceRepository.monthlyAttendance();
    }


    /**
     * @Method Name : deptLateRate
     * @작성일 : 2026. 3. 11.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 대시보드 용 부서별 지각률 조회
     * @param
     * @return
     */
    @GetMapping("/api/dashboard/dept-late-rate")
    @ResponseBody
    public List<Object[]> deptLateRate(){
        return attendanceRepository.deptLateRate();
    }

}
