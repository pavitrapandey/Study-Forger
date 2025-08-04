package com.studyForger.Study_Forger.Security.RefreshToken;

import lombok.*;

import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
public class RefreshTokenDto{

    private int id;
    private String token;
    private Instant expiryDate;

}