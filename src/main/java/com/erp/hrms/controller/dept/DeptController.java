package com.erp.hrms.controller.dept;


import com.erp.hrms.Entity.Dept;
import com.erp.hrms.Entity.DeptHistory;
import com.erp.hrms.service.DeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class DeptController {

    private final DeptService deptService;

    /**
     * @Method Name : register
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 부서 view 페이지 이동
     * @param 
     * @return
     */
    @GetMapping("/dept/register")
    public String register() {
        return "dept/register";
    }

    /**
     * @Method Name : deptList
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 부서 리스트 조회
     * @param
     * @return
     */
    @GetMapping("/dept/list")
    @ResponseBody
    public List<Dept> deptList(@RequestParam(required = false) String searchDeptName,
                               @RequestParam(required = false) String searchDeptId) {

        return deptService.search(searchDeptName,searchDeptId);
    }

    /**
     * @Method Name : register
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 부서 리스트 생성
     * @param
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/dept/register")
    @ResponseBody
    public Dept register(@RequestBody Dept dept) {

        return deptService.register(dept);
    }

    /**
     * @Method Name : update
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 부서 리스트 수정
     * @param
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/dept/update/{id}")
    @ResponseBody
    public Dept update(@PathVariable Long id,
                       @RequestBody Dept dept) {

        return deptService.update(id, dept);
    }

    /**
     * @Method Name : delete
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 부서 리스트 삭제 (useYn 'Y' -> 'N')
     * @param
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/dept/delete/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        deptService.delete(id);
    }


    /**
     * @Method Name : history
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 부서이력조회 view 페이지 이동
     * @param
     * @return
     */
    @GetMapping("/dept/history")
    public String history() {
        return "dept/history";
    }

    /**
     * @Method Name : deptHisList
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 부서이력 리스트 조회
     * @param
     * @return
     */
    @GetMapping("/dept/hisList")
    @ResponseBody
    public List<DeptHistory> deptHisList(@RequestParam(required = false) String searchDeptName,
                                         @RequestParam(required = false) String searchDeptId) {

        return deptService.hisSearch(searchDeptName,searchDeptId);
    }

}
