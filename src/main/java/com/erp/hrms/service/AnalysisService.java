package com.erp.hrms.service;


import com.erp.hrms.Entity.AnalysisRequest;
import com.erp.hrms.Repository.AttendanceRepository;
import com.erp.hrms.dto.AttendanceSummaryDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalysisService {

    private final AttendanceRepository attendanceRepository;
    private final OpenAiService openAiService;

    public String perAnalysis(AnalysisRequest request){
        AttendanceSummaryDTO data =
                attendanceRepository.getPersonAttendance(
                        request.getName(),
                        request.getDateFrm(),
                        request.getDateTo()
                );

        // 2️⃣ AI 프롬프트 생성
        String prompt = buildPerPrompt(data);

        // 3️⃣ GPT 호출
        return openAiService.callGPT(prompt);
//        return prompt;
    }

    private String buildPerPrompt(AttendanceSummaryDTO data){

        return """
            다음은 직원 근태 데이터이다.
        
            근무일수: %d
            지각횟수: %d
            지각시간: %d분
            초과근무: %d분
        
            위 데이터를 기반으로 근태 분석 리포트를 작성해라.
            문제점과 개선점도 포함해라.
    """.formatted(
                data.getWorkDays(),
                data.getLateCount(),
                data.getLateMinutes(),
                data.getOvertimeMinutes()
        );

    }

    public String deptAnalysis(AnalysisRequest request) {
        AttendanceSummaryDTO data =
                attendanceRepository.getDeptAttendance(
                        request.getDeptId(),
                        request.getDateFrm(),
                        request.getDateTo()
                );

        // 2️⃣ AI 프롬프트 생성
        String prompt = buildDeptPrompt(data);

        // 3️⃣ GPT 호출
        return openAiService.callGPT(prompt);
    }

    private String buildDeptPrompt(AttendanceSummaryDTO data){

        return """
        다음은 특정 부서의 근태 데이터이다.
        
        총 근무일수: %d
        총 지각 횟수: %d
        총 지각 시간: %d분
        총 초과근무 시간: %d분
        평균 초과근무 시간: %.1f분
        지각률: %.2f%%
        
        위 데이터를 기반으로 부서 근태 분석 리포트를 작성해라.
        부서의 근태 특징과 문제점, 개선 방안을 포함해라.
        """.formatted(
                data.getWorkDays(),
                data.getLateCount(),
                data.getLateMinutes(),
                data.getOvertimeMinutes(),
                data.getAvgOvertime(),
                data.getLateRate()
        );
    }

    public String abAnalysis(AnalysisRequest request) {

        List<AttendanceSummaryDTO> data =
                attendanceRepository.getAbAttendance();

        return buildAbnormalPrompt(data);

    }

    private String buildAbnormalPrompt(List<AttendanceSummaryDTO> list) {

        StringBuilder sb = new StringBuilder();

        sb.append("최근 3개월 기준 지각이 많은 직원 목록이다.\n\n");

        for(AttendanceSummaryDTO d : list){

            sb.append("이름 : ").append(d.getName())
                    .append(", 지각 횟수 : ").append(d.getLateCount())
                    .append(", 지각 시간 : ").append(d.getLateMinutes())
                    .append("분")
                    .append(", 지각률 : ").append(d.getLateRate())
                    .append("\n");
        }

        sb.append("\n위 데이터를 기반으로 조직의 근태 문제점을 분석하고 개선 방안을 제시해라.");

        return openAiService.callGPT(sb.toString());
    }

}
