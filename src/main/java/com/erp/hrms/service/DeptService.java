package com.erp.hrms.service;

import com.erp.hrms.Entity.Dept;
import com.erp.hrms.Entity.DeptHistory;
import com.erp.hrms.Repository.DeptRepository;
import com.erp.hrms.Repository.DeptHistoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DeptService {

    private final DeptRepository deptRepository;
    private final DeptHistoryRepository deptHistoryRepository;

    public List<Dept> list() {
        return deptRepository.findAll();
    }

    @Transactional
    public Dept register(Dept dept) {

        dept.setUseYn("Y");
        dept.setCreatedDt(LocalDateTime.now());
        Dept savedDept = deptRepository.save(dept);

        DeptHistory history = new DeptHistory();
        history.setDeptId(savedDept.getDeptId());
        history.setDeptName(savedDept.getDeptName());
        history.setStartDt(savedDept.getStartDt());
        history.setEndDt(savedDept.getEndDt());
        history.setCreatedDt(LocalDateTime.now());

        deptHistoryRepository.save(history);

        return savedDept;
    }

    public Dept update(Long id, Dept dept) {

        Dept findDept = deptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("부서 없음"));

        findDept.setDeptName(dept.getDeptName());
        findDept.setDeptId(dept.getDeptId());
        findDept.setStartDt(dept.getStartDt());
        findDept.setEndDt(dept.getEndDt());
        findDept.setModifiedDt(LocalDateTime.now());

        return deptRepository.save(findDept);
    }

    public List<Dept> search(String searchDeptName, String searchDeptId) {
        return deptRepository.searchDept(searchDeptName, searchDeptId);
    }

    public void delete(Long id) {
        Dept dept = deptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("부서 없음"));
        dept.setUseYn("N");
        dept.setEndDt(LocalDate.now());
        dept.setModifiedDt(LocalDateTime.now());
        deptRepository.save(dept);
    }

    public List<DeptHistory> hisSearch(String searchDeptName, String searchDeptId) {
        return deptHistoryRepository.searchDeptHis(searchDeptName,searchDeptId);
    }
}
