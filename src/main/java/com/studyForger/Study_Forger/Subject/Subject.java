package com.studyForger.Study_Forger.Subject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.studyForger.Study_Forger.Topic.Topic;
import com.studyForger.Study_Forger.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "subjects")
@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class Subject{

    @Id
    private String id;
    private String subjectName;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "created_by_id")
    @ToString.Exclude // Important to avoid LazyInitializationException in logs
    private User createdBy;

    @OneToMany(mappedBy = "subject", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // prevents infinite recursion in response serialization
    private List<Topic> topics; // foreign key to the Topic entity

    // Additional fields can be added as needed
}
