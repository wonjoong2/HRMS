package com.erp.hrms.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "departments")
@Getter
@Setter
public class Dept {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // 진짜 PK (절대 안 바뀜)

    @Column(unique = true)
    private String deptId;

    private String deptName;
    private String useYn;
    private LocalDate startDt;
    private LocalDate endDt;
    private LocalDateTime createdDt;
    private LocalDateTime modifiedDt;

}
