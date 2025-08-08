package com.studyForger.Study_Forger.User;


import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.studyForger.Study_Forger.Role.Role;
import com.studyForger.Study_Forger.Subject.Subject;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class User implements UserDetails {
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
    @JsonIgnore
    private List<Subject> subjects;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
     private List<Role> roles=new ArrayList<>();



    @Override
    public String getUsername(){
        return username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return roles.stream()
                .map(
                        role -> new SimpleGrantedAuthority(role.getRoleName()))
                                     .collect(Collectors.toList()
                                     );
    }

    @Override
    public String getPassword() {
        return password;
    }


    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}


