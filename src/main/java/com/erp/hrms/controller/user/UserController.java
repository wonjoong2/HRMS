package com.erp.hrms.controller.user;


import com.erp.hrms.dto.UserResponseDTO;
import com.erp.hrms.dto.UserSaveDTO;
import com.erp.hrms.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@Slf4j
public class UserController {

    private final UserService userService;

    /**
     * @Method Name : register
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 사원관리 view 페이지 이동
     * @param
     * @return
     */
    @GetMapping("/user/register")
    public String register() {
        return "user/register";
    }

    /**
     * @Method Name : userList
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 사원 리스트 조회
     * @param name, email, status
     * @return
     */
    @GetMapping("/user/list")
    @ResponseBody
    public List<UserResponseDTO> userList(@RequestParam(required = false) String name,
                                          @RequestParam(required = false) String email,
                                          @RequestParam(required = false) String status) {

        return userService.search(name, email, status);
    }

    /**
     * @Method Name : register
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 사원 리스트 생성
     * @param 
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/user/register")
    @ResponseBody
    public UserResponseDTO register(@RequestBody UserSaveDTO dto) {

        System.out.println("===== INSERT 요청 =====");
        System.out.println("dept_id: " + dto.getDept_id());
        System.out.println("name: " + dto.getName());
        System.out.println("email: " + dto.getEmail());
        System.out.println("password: " + dto.getPassword());
        System.out.println("hire_date: " + dto.getHire_date());
        System.out.println("status: " + dto.getStatus());

        return userService.register(dto);
    }

    /**
     * @Method Name : update
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 사원 리스트 수정
     * @param
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/user/update/{id}")
    @ResponseBody
    public UserResponseDTO update(@PathVariable Long id,
                       @RequestBody UserSaveDTO dto) {

        return userService.update(id, dto);
    }

    /**
     * @Method Name : delete
     * @작성일 : 2026. 3. 12.
     * @작성자 : "KWJ"
     * @변경이력 :
     * @Method 설명 : 사원 리스트 삭제 (useYn 'Y' -> 'N')
     * @param
     * @return
     */
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/user/delete/{id}")
    @ResponseBody
    public void delete(@PathVariable Long id) {
        userService.delete(id);
    }

}
