package com.studyForge.Study_Forge.Topic;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.studyForge.Study_Forge.Subject.Subject;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "topics")
public class Topic{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column(nullable = false)
    private String topicName;
    @Column(nullable = false)
    private String description;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date createdAt; // track when the topic was created
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy HH:mm:ss")
    private Date updatedAt; // track last updated


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

    // Additional fields can be added as needed
}
