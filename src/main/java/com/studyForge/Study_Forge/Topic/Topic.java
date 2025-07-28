package com.studyForge.Study_Forge.Topic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.studyForge.Study_Forge.Revision.Revision;
import com.studyForge.Study_Forge.Subject.Subject;
import com.studyForge.Study_Forge.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "topics")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Topic{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String topicName;
    @Column(nullable = false)
    private String description;



    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime createdAt; // track when the topic was created
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime updatedAt; // track last updated


    private boolean isCompleted; // flag to indicate if the topic is completed or not

    private boolean have_revised ; // flag to indicate if the topic has been revised or not

    @Enumerated(EnumType.STRING) // Enum mapping
    @Column(nullable = false)
    private Priority priority;
    public enum Priority {
        LOW,MEDIUM,HIGH
    };

    @Enumerated(EnumType.STRING) // Enum mapping for difficulty
    @Column(nullable = false)
    private Difficulty difficulty; // Assuming Difficulty is an enum defined elsewhere
    public enum Difficulty {
        EASY, MEDIUM, HARD
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    private Subject subject; // Assuming this is a foreign key to the Subject entity

    @OneToMany(mappedBy = "topic", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // prevents infinite recursion in response serialization
    private List<Revision> revisions; // list of revisions for this topic>

    // Sm-2 Fields
    double easeFactor; //default 2.5
    int repetition;    // default 0

    @Column(name = "interval_days")
    int interval;      // default 0
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime lastReviewDate; // track last review date, default today;Date lastReviewDate; // track last review date, default today;
    private LocalDateTime nextReviewDate; // track the latest review date, default today;Date newestReviewDate; // track the latest review date, default today;
}
