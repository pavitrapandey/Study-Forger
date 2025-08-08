package com.studyForger.Study_Forger.Subject;

import com.studyForger.Study_Forger.User.UserDto;
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

}
