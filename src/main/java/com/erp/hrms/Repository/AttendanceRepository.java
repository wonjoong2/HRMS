package com.erp.hrms.Repository;

import com.erp.hrms.Entity.Attendance;
import com.erp.hrms.dto.AttendanceSummaryDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {

    @Query("SELECT a FROM Attendance a JOIN FETCH a.user u " +
            "WHERE (:name IS NULL OR u.name LIKE %:name%) " +
            "AND (:workDate IS NULL OR a.work_date = :workDate)")
    Page<Attendance> search(
            @Param("name") String name,
            @Param("workDate") LocalDate workDate,
            Pageable pageable
    );
    @Query(value = """
        SELECT 
            COUNT(*) as workDays,
            SUM(CASE WHEN late_minutes > 0 THEN 1 ELSE 0 END) as lateCount,
            SUM(late_minutes) as lateMinutes,
            SUM(overtime_minutes) as overtimeMinutes
        FROM attendance a
        JOIN users u ON a.user_id = u.id
        WHERE u.name = :name
        AND a.work_date BETWEEN :dateFrm AND :dateTo
        """, nativeQuery = true)
    AttendanceSummaryDTO getPersonAttendance(
            String name,
            String dateFrm,
            String dateTo
    );


    @Query(value = """
            SELECT 
                COUNT(*) as workDays,
                SUM(CASE WHEN late_minutes > 0 THEN 1 ELSE 0 END) as lateCount,
                COALESCE(SUM(late_minutes),0) as lateMinutes,
                COALESCE(SUM(overtime_minutes),0) as overtimeMinutes,
                AVG(overtime_minutes) as avgOvertime,
                ROUND(SUM(CASE WHEN late_minutes > 0 THEN 1 ELSE 0 END) / COUNT(*) * 100.0, 2) as lateRate
            FROM attendance a
            JOIN users u ON a.user_id = u.id
            WHERE u.dept_id = :deptId
            AND a.work_date BETWEEN :dateFrm AND :dateTo
            """, nativeQuery = true)
    AttendanceSummaryDTO getDeptAttendance(
            @Param("deptId") String deptId,
            @Param("dateFrm") String dateFrm,
            @Param("dateTo") String dateTo
    );

    @Query(value = """
        SELECT 
            u.name as name,
            COUNT(*) as lateCount,
            SUM(late_minutes) as lateMinutes,
            ROUND(COUNT(*) * 100.0 / (
                SELECT COUNT(*)\s
                FROM attendance a2\s
                WHERE a2.user_id = a.user_id
                AND a2.work_date >= DATE_SUB(CURDATE(), INTERVAL 3 MONTH)
            ),1) as lateRate
        FROM attendance a
        JOIN users u ON a.user_id = u.id
        WHERE late_minutes > 0
        AND a.work_date >= DATE_SUB(CURDATE(), INTERVAL 3 MONTH)
        GROUP BY u.name, a.user_id
        ORDER BY lateCount DESC
        LIMIT 5
        """, nativeQuery = true)
    List<AttendanceSummaryDTO> getAbAttendance();

    @Query("""
        SELECT COUNT(a)
        FROM Attendance a
        WHERE a.work_date = :today
        AND a.attendance_status IN ('정상','지각','조퇴','지각+조퇴')
    """)
    Long countAttendToday(@Param("today") LocalDate today);

    @Query("""
        SELECT COUNT(a)
        FROM Attendance a
        WHERE a.work_date = :today
        AND a.attendance_status IN ('지각','지각+조퇴')
    """)
    Long countLateToday(@Param("today") LocalDate today);

    @Query("""
        SELECT COUNT(u)
        FROM User u
        WHERE u.role = 'ROLE_USER'
            AND u.id NOT IN (
            SELECT a.user.id
            FROM Attendance a
            WHERE a.work_date = :today
        )
    """)
    Long countAbsentToday(@Param("today") LocalDate today);


    @Query("""
        SELECT MONTH(a.work_date), COUNT(a)
        FROM Attendance a
        WHERE YEAR(a.work_date) = YEAR(CURRENT_DATE)
        GROUP BY MONTH(a.work_date)
        ORDER BY MONTH(a.work_date)
    """)
    List<Object[]> monthlyAttendance();

    @Query("""
        SELECT d.deptName,
               SUM(CASE WHEN a.attendance_status LIKE '%지각%' THEN 1 ELSE 0 END) * 100.0 / COUNT(a)
        FROM Attendance a
        JOIN a.user u
        JOIN u.dept d
        GROUP BY d.deptName
        ORDER BY 2 DESC
    """)
    List<Object[]> deptLateRate();
}
