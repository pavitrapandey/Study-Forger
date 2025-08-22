package com.studyForger.Study_Forger.Security.Jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class JwtRequest {

    @NotBlank(message = "Username is required")
    @Schema(description = "Username of the user")
    String username;

    @NotBlank(message = "Password is required")
    @Schema(description = "Password of the user")
    String password;
}
