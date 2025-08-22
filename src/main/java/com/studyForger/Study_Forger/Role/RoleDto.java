package com.studyForger.Study_Forger.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RoleDto {

    private String id;

    @NotBlank(message = "Role name is required")
    @Schema(description = "Role name of the role")
    private String roleName;

}
