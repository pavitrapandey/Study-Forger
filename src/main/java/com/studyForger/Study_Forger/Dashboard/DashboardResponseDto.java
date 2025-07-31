package com.studyForger.Study_Forger.Dashboard;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DashboardResponseDto{
    private int totalTopics;
    private int totalTopicsCompleted;

    @Override
    public String toString() {
        return "Dashboard: " +
                "totalTopicsForToday=" + totalTopics +
                ", totalTopicsCompletedToday=" + totalTopicsCompleted
                ;
    }
}
