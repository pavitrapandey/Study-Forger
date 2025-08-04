package com.studyForger.Study_Forger.Security.Jwt;

import com.studyForger.Study_Forger.Security.RefreshToken.RefreshTokenDto;
import com.studyForger.Study_Forger.User.UserDto;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    private String token;
    UserDto user;

    private RefreshTokenDto refreshToken;

}