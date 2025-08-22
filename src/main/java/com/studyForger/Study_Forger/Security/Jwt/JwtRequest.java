package com.studyForger.Study_Forger.Security.Jwt;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtRequest {

    String username;
    String password;
}
