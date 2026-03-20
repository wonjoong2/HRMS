package com.erp.hrms.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(name = "work_schedule")
@Getter
@Setter
public class WorkSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String deptId;
    private String deptName;

    private LocalTime startTime;
    private LocalTime endTime;
    private int breakMinutes;

    private String useYn;
    private LocalDateTime createdDt;
    private LocalDateTime modifiedDt;
}
