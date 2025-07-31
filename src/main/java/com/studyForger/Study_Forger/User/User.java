package com.studyForger.Study_Forger.User;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.studyForger.Study_Forger.Role.Role;
import com.studyForger.Study_Forger.Subject.Subject;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Role> roles=new ArrayList<>();
}


