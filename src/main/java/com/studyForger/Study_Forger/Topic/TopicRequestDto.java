package com.studyForger.Study_Forger.Topic;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TopicRequestDto {



    private String id;

    @NotBlank(message = "Topic name is required")
    @Schema(description = "Name of the topic")
    private String topicName;

    @Schema(description = "Description of the topic")
    private String description;

    @Schema(description = "Priority of the topic")
    private String priority; // Assuming priority is a string representation of the enum, e.g., "LOW", "MEDIUM", "HIGH"

    @Schema(description = "Difficulty of the topic")
    private String difficulty; // Assuming difficulty is a string representation of the enum, e.g., "EASY", "MEDIUM", "HARD"

    @Schema(description = "Revised status of the topic")
    private boolean have_revised ;
}
