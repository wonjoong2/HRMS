package com.erp.hrms.controller.attendance;

import com.erp.hrms.Entity.Dept;
import com.erp.hrms.Entity.Holiday;
import com.erp.hrms.service.AttendanceService;
import com.erp.hrms.service.HolidayService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class HolidayController {

    private final HolidayService holidayService;

    /**
     * @Method Name : holiday
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 공휴일관리 view 페이지 이동
     * @param
     * @return
     */
    @GetMapping("/holiday/page")
    public String holiday() {

        return "attendance/holiday";
    }

    /**
     * @Method Name : holidayList
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 공휴일관리 리스트 조회
     * @param
     * @return
     */
    @GetMapping("/holiday/list")
    @ResponseBody
    public List<Holiday> holidayList(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String holidayDate) {

        LocalDate parsedDate = (holidayDate != null && !holidayDate.isBlank())
                ? LocalDate.parse(holidayDate)
                : null;

        return holidayService.holidaySearch(name, parsedDate);
    }

    /**
     * @Method Name : holidayRegister
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 공휴일 생성
     * @param
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/holiday/register")
    @ResponseBody
    public Holiday holidayRegister(@RequestBody Holiday holiday) {

        return holidayService.holidayRegister(holiday);
    }

    /**
     * @Method Name : holidayRegister
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 공휴일 수정
     * @param
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/holiday/update/{id}")
    @ResponseBody
    public Holiday update(@PathVariable Long id,
                       @RequestBody Holiday holiday) {

        return holidayService.update(id, holiday);
    }

    /**
     * @Method Name : holidayRegister
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 공휴일 삭제 (useYn 'Y' -> 'N')
     * @param
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/holiday/delete/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        holidayService.delete(id);
    }
}
