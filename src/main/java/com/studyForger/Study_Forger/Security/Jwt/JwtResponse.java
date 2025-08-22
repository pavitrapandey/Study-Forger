package com.studyForger.Study_Forger.Security.Jwt;

import com.studyForger.Study_Forger.Security.RefreshToken.RefreshTokenDto;
import com.studyForger.Study_Forger.User.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtResponse {
    @Schema(description = "JWT Response")
    private String token;

    @Schema(description = "User information")
    String user;

    @Schema(description = "Refresh Token")
    private RefreshTokenDto refreshToken;

}