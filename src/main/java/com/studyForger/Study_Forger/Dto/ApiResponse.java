package com.studyForger.Study_Forger.Dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ApiResponse {

    @Schema(description = "Api message provided to the user")
    private String message;

    @Schema(description = "Api success provided to the user, if it's true or false")
    private boolean success;

    @Schema(description = "Api status provided to the user")
    private HttpStatus status;
}
