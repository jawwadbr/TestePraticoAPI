package com.jawbr.testepratico.repository;

import com.jawbr.testepratico.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
