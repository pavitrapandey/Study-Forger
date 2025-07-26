package com.studyForge.Study_Forge.Subject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.studyForge.Study_Forge.Topic.Topic;
import com.studyForge.Study_Forge.User.User;
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
