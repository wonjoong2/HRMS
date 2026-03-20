package com.erp.hrms.Repository;

import com.erp.hrms.Entity.User;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByEmail(String email);

    @Query("""
    SELECT u FROM User u
    WHERE (:name IS NULL OR :name = '' OR u.name LIKE %:name%)
      AND (:email IS NULL OR :email = '' OR u.email LIKE %:email%)
      AND (:status IS NULL OR :status = '' OR u.status = :status)
      AND u.useYn ='Y'
""")
    List<User> searchUsers(@Param("name") String name,
                           @Param("email") String email,
                           @Param("status") String status);

    Long countByRole(String role);


    List<User> findByRole(String role);

    Optional<User> findByName(String name);
}
