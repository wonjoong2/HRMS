package com.erp.hrms.controller;

import com.erp.hrms.Entity.Attendance;
import com.erp.hrms.Entity.User;
import com.erp.hrms.Entity.WorkSchedule;
import com.erp.hrms.Repository.AttendanceRepository;
import com.erp.hrms.Repository.HolidayRepository;
import com.erp.hrms.Repository.UserRepository;
import com.erp.hrms.Repository.WorkScheduleRepository;
import com.erp.hrms.service.EmailService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.datafaker.Faker;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

@Controller
@RequiredArgsConstructor
@Slf4j
public class loginController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AttendanceRepository attendanceRepository;
    private final WorkScheduleRepository workScheduleRepository;
    private final HolidayRepository holidayRepository;
    private final EmailService emailService;

    /**
     * @Method Name : Login
     * @작성일 : 2026. 3. 11.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 로그인 view 페이지 이동
     * @param
     * @return
     */
    @RequestMapping("/login")
    public String Login(){

        return "login";
    }

    /**
     * @Method Name : signup
     * @작성일 : 2026. 3. 11.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 회원가입
     * @param
     * @return
     */
    @PostMapping("/signup")
    public String signup(User user, HttpSession session, @RequestParam String authCode, RedirectAttributes ra){

        String sessionCode = (String) session.getAttribute("emailAuthCode");

        if(sessionCode == null || !sessionCode.equals(authCode)){
            ra.addFlashAttribute("signupFail", true);
            return "redirect:login";
        }

        session.removeAttribute("emailAuthCode");

        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.setHire_date(LocalDate.now());
        user.setStatus("재직");
        user.setUseYn("Y");
        user.setRole("ROLE_USER");
        user.setCreated_dt(LocalDateTime.now());

        userRepository.save(user);

        log.info("회원가입 완료 : {}", user.getEmail());
        ra.addFlashAttribute("signupSuccess", true);

        return "redirect:login";
    }

    /**
     * @Method Name : sendAuthCode
     * @작성일 : 2026. 3. 11.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 이메일 인증번호 발송
     * @param
     * @return
     */
    @PostMapping("/sendAuthCode")
    @ResponseBody
    public String sendAuthCode(@RequestParam String email,
                               HttpSession session){

        String code = emailService.sendAuthCode(email);

        session.setAttribute("emailAuthCode", code);

        return "OK";
    }

    /**
     * @Method Name : resetPassword
     * @작성일 : 2026. 3. 11.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 비밀번호 재설정
     * @param
     * @return
     */
    @PostMapping("/resetPassword")
    @ResponseBody
    public String resetPassword(@RequestParam String email) {

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("이메일 없음"));

        // 임시 비밀번호 생성
        String tempPassword = UUID.randomUUID().toString().substring(0,8);

        // 암호화 저장
        user.setPassword(passwordEncoder.encode(tempPassword));
        userRepository.save(user);

        // 이메일 발송
        emailService.sendTempPassword(email, tempPassword);

        return "OK";
    }
}
