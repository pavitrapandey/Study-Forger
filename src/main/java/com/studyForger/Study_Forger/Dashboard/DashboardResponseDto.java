package com.studyForger.Study_Forger.Dashboard;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponseDto{

    @Schema(description = "Total Number of Topics have to review on that particular day")
    private int totalTopics;

    @Schema(description = "Total Number of topics have been review so far")
    private int totalTopicsCompleted;

}
