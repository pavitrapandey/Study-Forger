package com.studyForge.Study_Forge.Revision;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.time.LocalDateTime;
import java.util.List;

public interface RevisionRepository extends JpaRepository<Revision, String>{
    List<Revision> findByNextReviewDateBefore(LocalDateTime date);
}