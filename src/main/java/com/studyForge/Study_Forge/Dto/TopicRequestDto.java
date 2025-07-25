package com.studyForge.Study_Forge.Dto;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicRequestDto {



    private String id;
    private String topicName;
    private String description;
    private String priority; // Assuming priority is a string representation of the enum, e.g., "LOW", "MEDIUM", "HIGH"
    private String difficulty; // Assuming difficulty is a string representation of the enum, e.g., "EASY", "MEDIUM", "HARD"
    private boolean have_revised ;
}
