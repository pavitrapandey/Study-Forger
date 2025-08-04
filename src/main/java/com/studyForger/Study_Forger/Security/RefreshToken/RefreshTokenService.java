package com.studyForger.Study_Forger.Security.RefreshToken;

import com.studyForger.Study_Forger.User.UserDto;

public interface RefreshTokenService {
    //create
    RefreshTokenDto createRefreshToken(String username);

    // find by token

    RefreshTokenDto findByToken(String token);

    //verify token
    RefreshTokenDto verifyToken(RefreshTokenDto token);

    //get user
    UserDto getUser(RefreshTokenDto token);
}
