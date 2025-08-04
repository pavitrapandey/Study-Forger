package com.studyForger.Study_Forger.Role;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, String>{
  Optional<Role> findByRoleName(String roleNormal);
}