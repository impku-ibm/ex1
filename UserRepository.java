package com.pradeep.demo_jwt_authentication.repository;

import com.pradeep.demo_jwt_authentication.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository  extends JpaRepository<User, Long> {
    User findByUsernameOrEmail(String usernameOrEmail, String usernameOrEmail1);
}
