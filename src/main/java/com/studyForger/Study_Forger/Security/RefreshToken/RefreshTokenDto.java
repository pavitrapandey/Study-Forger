package com.studyForger.Study_Forger.Security.RefreshToken;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RefreshTokenDto{

    private int id;

    @Schema(description = "JWT Response")
    private String token;

    @Schema(description = "User information")
    private Instant expiryDate;

}