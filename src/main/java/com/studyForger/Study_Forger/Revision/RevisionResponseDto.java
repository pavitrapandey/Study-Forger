package com.studyForger.Study_Forger.Revision;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevisionResponseDto
{

    @Schema(description = "Gives Topic Name")
    private String topic;

    @Schema(description = "Provide the calculated ease factor for the topic")
    private double easeFactor;

    @Schema(description = "Provide the number of repetitions for the topic so far")
    private int repetition;

    @Schema(description = "Number of Days for next review")
    private int interval;

    @Schema(description = "Last Quality Score given to the topic")
    private int lastQualityScore;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Schema(description = "Last Review Date")
    private LocalDate lastReviewDate;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    @Schema(description = "Next Review Date")
    private LocalDate nextReviewDate;


}
