package com.erp.hrms.controller.attendance;

import com.erp.hrms.Entity.Attendance;
import com.erp.hrms.Entity.LeaveRequest;
import com.erp.hrms.service.LeaveRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDate;
import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class LeaveRequestController {

    private final LeaveRequestService leaveRequestService;

    /**
     * @Method Name : leaveRequest
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 휴가관리 view 페이지 이동
     * @param
     * @return
     */
    @GetMapping("/leaveRequest/page")
    public String leaveRequest() {

        return "attendance/leaveRequest";
    }

    /**
     * @Method Name : leaveRequestList
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 휴가관리 리스트 조회
     * @param
     * @return
     */
    @GetMapping("/leaveRequest/list")
    @ResponseBody
    public List<LeaveRequest> leaveRequestList(@RequestParam(required = false) String name) {

        return leaveRequestService.search(name);
    }
}
