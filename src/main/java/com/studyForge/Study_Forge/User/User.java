package com.studyForge.Study_Forge.User;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.studyForge.Study_Forge.Subject.Subject;
import com.studyForge.Study_Forge.Topic.Topic;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User {
    @Id
    private String id;
    @Column(unique = true)
    private String username;
    @Column(unique = true)
    private String email;
    private String name;
    private String password;
    private String about;
    private String imageName;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "createdBy", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnore // prevents infinite recursion in response serialization
    private List<Subject> subjects;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true) // prevents infinite recursion in response serialization
    @JsonIgnore
    private List<Topic> topics;

}


