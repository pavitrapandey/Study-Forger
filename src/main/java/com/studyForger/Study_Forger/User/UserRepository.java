package com.studyForger.Study_Forger.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>{
    User findByUsername(String username);
    Optional<User> findByEmail(String email);


}
