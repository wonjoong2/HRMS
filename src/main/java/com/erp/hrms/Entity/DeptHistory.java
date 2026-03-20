package com.erp.hrms.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "departments_history")
@Getter
@Setter
public class DeptHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deptId;
    private String deptName;

    private LocalDate startDt;
    private LocalDate endDt;
    private LocalDateTime createdDt;
}
