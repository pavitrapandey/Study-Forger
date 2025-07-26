package com.studyForge.Study_Forge.Subject;

import com.studyForge.Study_Forge.User.UserDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubjectDto {

    private String id;
    private String subjectName;
    private String description;
    private UserDto createdBy; // Assuming UserDto is another DTO representing the creator of the subject
}
