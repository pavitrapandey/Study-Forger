package com.studyForger.Study_Forger.Topic;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicResponseDto{

    private String id;

    @Schema(description = "Provides the given name of the topic")
    private String topicName;

    @Schema(description = "Provides the description of the topic")
    private String description;

    @Schema(description = "Provides the priority of the topic")
    private String priority;

    @Schema(description = "Provides the difficulty of the topic")
    private String difficulty;

    @Schema(description = "Provides the name of the Subject of the topic")
    private String subject;

    @Schema(description = "Provides the time when the topic was created")
    private String createdAt;

    @Schema(description = "Provides the time when the topic was last updated")
    private String updatedAt;

}
