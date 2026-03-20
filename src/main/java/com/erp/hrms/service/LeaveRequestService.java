package com.erp.hrms.service;


import com.erp.hrms.Entity.Attendance;
import com.erp.hrms.Entity.LeaveRequest;
import com.erp.hrms.Repository.LeaveRequestRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveRequestService {

    private final LeaveRequestRepository leaveRequestRepository;


    public List<LeaveRequest> search(String name) {
        return leaveRequestRepository.search(name);
    }
}
