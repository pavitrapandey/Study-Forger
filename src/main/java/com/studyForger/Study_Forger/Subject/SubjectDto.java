package com.studyForger.Study_Forger.Subject;

import com.studyForger.Study_Forger.User.UserDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDto {

    private String id;

    @NotBlank(message = "Subject name is required")
    @Schema(description = "Subject name of the subject")
    private String subjectName;

    @Schema(description = "Subject description of the subject")
    private String description;

}
