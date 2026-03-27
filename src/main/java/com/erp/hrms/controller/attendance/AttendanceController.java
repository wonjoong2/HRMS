package com.erp.hrms.controller.attendance;


import com.erp.hrms.Entity.AnalysisRequest;
import com.erp.hrms.Entity.Attendance;
import com.erp.hrms.service.AnalysisService;
import com.erp.hrms.service.AttendanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequiredArgsConstructor
@Slf4j
public class AttendanceController {

    private final AttendanceService attendanceService;
    private final AnalysisService analysisService;

    /**
     * @Method Name : register
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 근태현황조회 view 페이지 이동
     * @param
     * @return
     */
    @GetMapping("/attendance/page")
    public String register() {

        return "attendance/page";
    }

    /**
     * @Method Name : register
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 근태현황 리스트 조회
     * @param : name, workDate
     * @return
     */
    @GetMapping("/attendance/list")
    @ResponseBody
    public Page<Attendance> attendanceList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String workDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        LocalDate parsedDate = (workDate != null && !workDate.isBlank())
                ? LocalDate.parse(workDate)
                : null;

        Pageable pageable = PageRequest.of(page, size);

        return attendanceService.search(name, parsedDate, pageable);
    }

    /**
     * @Method Name : register
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : AI 근태분석 popup 페이지 이동
     * @param : name, workDate
     * @return
     */
    @GetMapping("/attendance/ai/pop")
    public String AIPop() {

        return "attendance/pop/popup";
    }

    /**
     * @Method Name : perAnalysis
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : AI 근태분석 -> 개인 근태 분석
     * @param : 
     * @return
     */
    @PostMapping("/ai/attendance/perAnalysis")
    @ResponseBody
    public String perAnalysis(@RequestBody AnalysisRequest request){

        return analysisService.perAnalysis(request);

    }

    /**
     * @Method Name : deptAnalysis
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : AI 근태분석 -> 부서 근태 분석
     * @param : 
     * @return
     */
    @PostMapping("/ai/attendance/deptAnalysis")
    @ResponseBody
    public String deptAnalysis(@RequestBody AnalysisRequest request){

        return analysisService.deptAnalysis(request);

    }

    /**
     * @Method Name : abAnalysis
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : AI 근태분석 -> 이상 근태 분석
     * @param : 
     * @return
     */
    @PostMapping("/ai/attendance/abAnalysis")
    @ResponseBody
    public String abAnalysis(@RequestBody AnalysisRequest request){

        return analysisService.abAnalysis(request);

    }

}
