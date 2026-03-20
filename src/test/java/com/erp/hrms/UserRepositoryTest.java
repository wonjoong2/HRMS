package com.erp.hrms;

import com.erp.hrms.Entity.User;
import com.erp.hrms.Repository.UserRepository;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    void 사용자_저장_조회_테스트() {

        // given
        User user = new User();
        user.setName("test");
        user.setEmail("test@test.com");
        user.setPassword("1234");

        // when
        userRepository.save(user);
        Optional<User> found = userRepository.findByName("test");

        // then
        assertTrue(found.isPresent());
        assertEquals("test", found.get().getName());
    }

    @Test
    void 사용자_없을때_조회_테스트() {

        // when
        Optional<User> found = userRepository.findByName("없는사용자");

        // then
        assertFalse(found.isPresent());
    }

    @Test
    void 사용자_없으면_예외발생() {

        Optional<User> found = userRepository.findByName("없는사용자");

        assertThrows(RuntimeException.class, () -> {
            found.orElseThrow(() -> new RuntimeException("User not found"));
        });
    }
}
