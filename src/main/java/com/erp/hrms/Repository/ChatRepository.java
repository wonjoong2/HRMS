package com.erp.hrms.Repository;

import com.erp.hrms.Entity.Attendance;
import com.erp.hrms.Entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ChatRepository  extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> findTop100ByDeptIdOrderByCreatedDtAsc(String deptId);

}
