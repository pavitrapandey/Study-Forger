package com.studyForger.Study_Forger.User;

import com.studyForger.Study_Forger.Role.RoleDto;
import lombok.*;

import java.util.List;

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
    private List<RoleDto> roles;
}
