package com.studyForge.Study_Forge.Revision;

import com.studyForge.Study_Forge.Topic.Topic;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "revisions")
public class Revision{

    @Id
    private String id;
    private double easeFactor;
    @Column(name = "interval_days")
    private int interval;
    private int repetition;
    private int qualityScore;


    private LocalDateTime lastReviewDate; // Unix timestamp for the last review date
    private LocalDateTime nextReviewDate; //next revision date

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    Topic topic;

}
