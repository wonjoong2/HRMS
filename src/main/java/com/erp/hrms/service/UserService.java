package com.erp.hrms.service;

import com.erp.hrms.Entity.Dept;
import com.erp.hrms.Entity.User;
import com.erp.hrms.Repository.DeptRepository;
import com.erp.hrms.Repository.UserRepository;
import com.erp.hrms.dto.UserResponseDTO;
import com.erp.hrms.dto.UserSaveDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final DeptRepository deptRepository;
    private final PasswordEncoder passwordEncoder;

    public List<User> list() {

        return userRepository.findAll();
    }

    public UserResponseDTO register(UserSaveDTO dto) {

        Dept dept = deptRepository.findByDeptId(dto.getDept_id())
                .orElseThrow();

        User user = new User();
        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setHire_date(LocalDate.parse(dto.getHire_date()));
        user.setStatus(dto.getStatus());
        user.setCreated_dt(dto.getCreated_dt());
        user.setDept(dept);
        user.setUseYn("Y");

        User saved = userRepository.save(user);

        return new UserResponseDTO(saved);
    }

    public UserResponseDTO update(Long id, UserSaveDTO dto) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("사용자 없음"));

        user.setName(dto.getName());
        user.setEmail(dto.getEmail());
        user.setHire_date(LocalDate.parse(dto.getHire_date()));
        user.setStatus(dto.getStatus());
        user.setPassword(passwordEncoder.encode(dto.getPassword()));

        // 부서 변경 처리
        Dept dept = deptRepository.findByDeptId(dto.getDept_id())
                .orElseThrow(() -> new RuntimeException("부서 없음"));

        user.setDept(dept);

        User saved = userRepository.save(user);

        return new UserResponseDTO(saved);
    }

    public List<UserResponseDTO> search(String name, String email, String status) {

        List<User> users = userRepository.searchUsers(name, email, status);

        return users.stream()
                .map(UserResponseDTO::new)
                .toList();
    }

    public void delete(Long id) {

        User user = userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("부서 없음"));
        user.setUseYn("N");

        userRepository.save(user);
    }
}
