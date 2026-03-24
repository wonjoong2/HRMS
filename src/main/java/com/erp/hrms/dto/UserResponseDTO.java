package com.erp.hrms.dto;

import com.erp.hrms.Entity.User;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String dept_id;
    private String dept_name;
    private String hire_date;
    private String status;
    private String role;

    public UserResponseDTO(User user) {
        this.id = user.getId();
        this.name = user.getName();
        this.email = user.getEmail();
        this.hire_date = user.getHire_date().toString();
        this.status = user.getStatus();
        this.role = user.getRole();

        if(user.getDept() != null) {
            this.dept_id = user.getDept().getDeptId();
            this.dept_name = user.getDept().getDeptName();
        }
    }
}
