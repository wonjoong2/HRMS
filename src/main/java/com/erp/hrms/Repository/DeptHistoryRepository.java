package com.erp.hrms.Repository;

import com.erp.hrms.Entity.Dept;
import com.erp.hrms.Entity.DeptHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface DeptHistoryRepository extends JpaRepository<DeptHistory, Long> {


    @Query("""
        SELECT u FROM DeptHistory u
        WHERE (:searchDeptName IS NULL OR :searchDeptName = '' OR u.deptName LIKE %:searchDeptName%)
          AND (:searchDeptId IS NULL OR :searchDeptId = '' OR u.deptId LIKE %:searchDeptId%)
    """)
    List<DeptHistory> searchDeptHis(
            @Param("searchDeptName") String searchDeptName,
            @Param("searchDeptId") String searchDeptId);








//    @Query("""
//    SELECT u FROM Dept u
//    WHERE (:searchDeptName IS NULL OR :searchDeptName = '' OR u.deptName LIKE %:searchDeptName%)
//      AND (:searchDeptId IS NULL OR :searchDeptId = '' OR u.deptId LIKE %:searchDeptId%)
//      AND u.useYn ='Y'
//    """)
//    List<Dept> searchDept(
//            @Param("searchDeptName") String searchDeptName,
//            @Param("searchDeptId") String searchDeptId);
}
