package com.studyForger.Study_Forger.Revision;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RevisionTopicDto {
    //"topicName": "OSI Model",
    //        "description": "7 Layers",
    //        "priority": "HIGH",
    //        "difficulty": "MEDIUM",
    //        "subject": "CN",

    private String topicName;
    private String description;
    private String priority;
    private String difficulty;
    private String subject;
    private String lastReviewDate;
    private String nextReviewDate;
    private int revisionCount;
    private double easeFactor;
}
