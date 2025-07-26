package com.studyForge.Study_Forge.Subject;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SubjectRepository extends JpaRepository<Subject, String>{
    List<Subject> findBySubjectNameAndCreatedById(String subjectName, String userId);


    List<Subject> findByCreatedById(String createdById);
}
