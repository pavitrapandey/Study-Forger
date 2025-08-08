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
    private String priority;
    private String difficulty;
    private String subject;
    private String createdAt;
    private String updatedAt;

}
