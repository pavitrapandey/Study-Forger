package com.studyForge.Study_Forge.Entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

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
