package com.studyForger.Study_Forger.Role;

import com.studyForger.Study_Forger.User.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Role {

    @Id
    private String id;


    @Column(nullable = false)
    private String roleName;

    @ManyToMany(mappedBy = "roles",fetch = FetchType.LAZY)
    private List<User> users=new ArrayList<>();

}
