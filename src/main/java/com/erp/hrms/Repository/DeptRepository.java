package com.erp.hrms.Repository;

import com.erp.hrms.Entity.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface DeptRepository extends JpaRepository<Dept, Long> {

    Optional<Dept> findByDeptId(String dept_id);

    @Query("""
    SELECT u FROM Dept u
    WHERE (:searchDeptName IS NULL OR :searchDeptName = '' OR u.deptName LIKE %:searchDeptName%)
      AND (:searchDeptId IS NULL OR :searchDeptId = '' OR u.deptId LIKE %:searchDeptId%)
      AND u.useYn ='Y'
    """)
    List<Dept> searchDept(
            @Param("searchDeptName") String searchDeptName,
            @Param("searchDeptId") String searchDeptId);

}
