package com.erp.hrms.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public interface AttendanceSummaryDTO {

    //개인 + 부서
    Long getWorkDays();
    Long getLateCount();
    Long getLateMinutes();
    Long getOvertimeMinutes();

    //부서
    Double getAvgOvertime();
    Double getLateRate();

    //이상
    String getName();

}
