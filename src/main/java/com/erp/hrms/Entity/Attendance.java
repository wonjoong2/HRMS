package com.erp.hrms.Entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Entity
@Table(
        name = "attendance",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_user_workdate",
                        columnNames = {"user_id", "work_date"}
                )
        }
)
@Getter
@Setter
public class Attendance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 사원 (users.id FK)
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    /**
     * 근무일자
     */
    @Column(name = "work_date", nullable = false)
    private LocalDate work_date;

    /**
     * 출근 시각
     */
    @Column(name = "check_in_time")
    private LocalTime check_in_time;

    /**
     * 퇴근 시각
     */
    @Column(name = "check_out_time")
    private LocalTime check_out_time;

    /**
     * 총 근무 시간(분)
     */
    @Column(name = "work_minutes")
    private Integer work_minutes = 0;

    /**
     * 지각 시간(분)
     */
    @Column(name = "late_minutes")
    private Integer late_minutes = 0;

    /**
     * 조퇴 시간(분)
     */
    @Column(name = "early_leave_minutes")
    private Integer early_leave_minutes = 0;

    /**
     * 초과 근무 시간(분)
     */
    @Column(name = "overtime_minutes")
    private Integer overtime_minutes = 0;

    /**
     * 근태 상태 (정상/지각/조퇴/결근)
     */
    @Column(name = "attendance_status", length = 20)
    private String attendance_status;

    /**
     * 생성일시
     */
    @Column(name = "created_dt", updatable = false)
    private LocalDateTime created_dt;

    /**
     * 수정일시
     */
    @Column(name = "updated_dt")
    private LocalDateTime updated_dt;

    /**
     * 저장 전 자동 세팅
     */
    @PrePersist
    public void prePersist() {
        this.created_dt = LocalDateTime.now();
    }

    /**
     * 수정 시 자동 세팅
     */
    @PreUpdate
    public void preUpdate() {
        this.updated_dt = LocalDateTime.now();
    }
}