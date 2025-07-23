package com.studyForge.Study_Forge.Dto;

import jakarta.persistence.Column;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String id;
    private String username;
    private String email;
    private String password;
    private String about;
    private String imageName;
}
