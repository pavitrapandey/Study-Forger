package com.studyForger.Study_Forger.Security.RefreshToken;

import com.studyForger.Study_Forger.User.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer>{
    Optional<RefreshToken> findByUser(User user);

    Optional<RefreshToken> findByToken(String token);

}