package com.studyForger.Study_Forger.Topic;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicResponseDto{

    private String id;
    private String topicName;
    private String description;
    private String priority; // Assuming priority is a string representation of the enum, e.g., "LOW", "MEDIUM", "HIGH"
    private String difficulty; // Assuming difficulty is a string representation of the enum, e.g., "EASY", "MEDIUM", "HARD"
    private String subject; // Assuming SubjectDto is another DTO representing the subject of the topic
    private String createdAt; // Assuming createdAt is a string representation of the date
    private String updatedAt; // Assuming updatedAt is a string representation of the date

}
