package com.studyForge.Study_Forge.Entity;

import jakarta.annotation.Nonnull;
import jakarta.persistence.*;
import lombok.*;
import org.antlr.v4.runtime.misc.NotNull;

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
    @Nonnull
    private User createdBy;

    // Additional fields can be added as needed
}
