package com.studyForger.Study_Forger.Revision;
import io.swagger.v3.oas.annotations.media.Schema;
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

    @Schema(description = "Gives Topic Name")
    private String topicName;

    @Schema(description = "Provide the description of the topic")
    private String description;

    @Schema(description = "Provide the priority of the topic")
    private String priority;

    @Schema(description = "Provide the difficulty of the topic")
    private String difficulty;

    @Schema(description = "Provide the subject name of the topic")
    private String subject;

    @Schema(description = "Last Review date given to the topic")
    private String lastReviewDate;

    @Schema(description = "Next Review date given to the topic")
    private String nextReviewDate;

    @Schema(description = "Number of revisions have been done so far for the topic")
    private int revisionCount;

    @Schema(description = "Provide thw Calculated Ease Factor for the topic")
    private double easeFactor;
}
