package com.studyForger.Study_Forger.Revision;

import com.studyForger.Study_Forger.Topic.Topic;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

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


    private LocalDate lastReviewDate; // Unix timestamp for the last review date
    private LocalDate nextReviewDate; //next revision date

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topic_id")
    Topic topic;

}
