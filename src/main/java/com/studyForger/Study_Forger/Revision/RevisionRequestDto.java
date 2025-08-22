package com.studyForger.Study_Forger.Revision;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevisionRequestDto{

    @Schema(description = "Topic ID to fetch topic for the revision")
    private String topicId;

    @Schema(description = "Quality score of the revision")
    private int qualityScore;
}
