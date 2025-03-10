package com.ramadan.dayone.demo.repository;

import com.ramadan.dayone.demo.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findByEmail(String email);
    Integer countByEmail(String email);
}
