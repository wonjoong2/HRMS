package com.erp.hrms.Repository;

import com.erp.hrms.Entity.WorkSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface WorkScheduleRepository extends JpaRepository<WorkSchedule, Long> {

    @Query("SELECT w FROM WorkSchedule w " +
            "WHERE (:name IS NULL OR w.deptName LIKE %:name%) " +
            "AND w.useYn = 'Y'"
    )
    List<WorkSchedule> search(@Param("name") String name);

    Optional<WorkSchedule> findByDeptId(String deptId);
}
