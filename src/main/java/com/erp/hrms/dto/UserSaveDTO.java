package com.erp.hrms.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserSaveDTO {

    private Long id;
    private String name;
    private String dept_id;
    private String email;
    private String password;
    private String hire_date;
    private String status;
    private String role;
    private LocalDateTime created_dt;

}
