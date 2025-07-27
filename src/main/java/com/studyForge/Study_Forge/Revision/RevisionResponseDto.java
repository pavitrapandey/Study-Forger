package com.studyForge.Study_Forge.Revision;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RevisionResponseDto
{

    private String topic;
    private double easeFactor;
    private int repetition;
    private int interval;
    private int lastQualityScore;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss a")
    private LocalDateTime lastReviewDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy hh:mm:ss a")
    private LocalDateTime nextReviewDate;

    @Override
    public String toString() {
        return "Your Task{" +
                "topic='" + topic + '\'' +
                ", easeFactor=" + easeFactor +
                ", repetition=" + repetition +
                ", interval=" + interval +
                ", lastQualityScore=" + lastQualityScore +
                ", lastReviewDate=" + lastReviewDate +
                ", nextReviewDate=" + nextReviewDate +
                "All The Best For Your Revision!"+
                '}';
    }
}
