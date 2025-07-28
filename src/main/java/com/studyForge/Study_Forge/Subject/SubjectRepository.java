package com.studyForge.Study_Forge.Subject;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, String>{
    Page<Subject> findBySubjectNameAndCreatedById(String subjectName, String userId, Pageable pageable);


    Page<Subject> findByCreatedById(String createdById, Pageable pageable);
}
