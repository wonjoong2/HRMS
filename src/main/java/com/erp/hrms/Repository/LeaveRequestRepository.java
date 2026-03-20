package com.erp.hrms.Repository;

import com.erp.hrms.Entity.Attendance;
import com.erp.hrms.Entity.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    @Query("""
        SELECT r FROM LeaveRequest r JOIN FETCH r.user u
        WHERE (:name IS NULL OR u.name LIKE %:name%)
    """)
    List<LeaveRequest> search(@Param("name") String name);
}
