package com.studyForger.Study_Forger.User;

import com.studyForger.Study_Forger.Role.RoleDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String id;

    @NotBlank(message = "Username is required")
    @Schema(description = "Username of the user")
    private String username;

    @NotBlank(message = "Email is required")
    @Pattern(regexp = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$", message = "Invalid email format")
    @Schema(description = "Email of the user")
    private String email;

    @NotBlank(message = "Name is required")
    @Schema(description = "Name of the user")
    private String name;

    @NotBlank(message = "Password is required")
    @Schema(description = "Password of the user")
    @Size(min = 4, max = 20, message = "Invalid Password")
    private String password;

    @Schema(description = "About the user")
    private String about;

    @Schema(description = "Image name of the user")
    private String imageName;

    @Schema(description = "Roles of the user, default is NORMAL")
    private List<RoleDto> roles;
}
