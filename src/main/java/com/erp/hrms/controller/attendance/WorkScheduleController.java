package com.erp.hrms.controller.attendance;


import com.erp.hrms.Entity.Dept;
import com.erp.hrms.Entity.WorkSchedule;
import com.erp.hrms.service.WorkScheduleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class WorkScheduleController {

    private final WorkScheduleService workScheduleService;

    /**
     * @Method Name : workSchedule
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 부서별 기준근무시간 view 페이지 이동
     * @param
     * @return
     */
    @GetMapping("/workSchedule/page")
    public String workSchedule() {

        return "attendance/workSchedule";
    }

    /**
     * @Method Name : workScheduleList
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 부서별 기준근무시간 리스트 조회
     * @param
     * @return
     */
    @GetMapping("/workSchedule/list")
    @ResponseBody
    public List<WorkSchedule> workScheduleList(
            @RequestParam(required = false) String name) {

        return workScheduleService.WorkScheduleSearch(name);
    }

    /**
     * @Method Name : register
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 부서별 기준근무시간 생성
     * @param
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/workSchedule/register")
    @ResponseBody
    public WorkSchedule register(@RequestBody WorkSchedule workSchedule) {

        return workScheduleService.WorkScheduleRegister(workSchedule);
    }

    /**
     * @Method Name : update
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 부서별 기준근무시간 생성
     * @param
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/workSchedule/update/{id}")
    @ResponseBody
    public WorkSchedule update(@PathVariable Long id,
                       @RequestBody WorkSchedule workSchedule) {

        return workScheduleService.update(id, workSchedule);
    }

    /**
     * @Method Name : delete
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 부서별 기준근무시간 삭제
     * @param
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/workSchedule/delete/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        workScheduleService.delete(id);
    }

}


