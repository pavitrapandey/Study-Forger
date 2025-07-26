package com.studyForge.Study_Forge.User;

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
    private String name;
    private String password;
    private String about;
    private String imageName;
}
