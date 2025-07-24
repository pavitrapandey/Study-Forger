package com.studyForge.Study_Forge.Repository;

import com.studyForge.Study_Forge.Entity.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SubjectRepository extends JpaRepository<Subject, String>{
    List<Subject> findBySubjectNameAndCreatedById(String subjectName, String userId);


    List<Subject> findByCreatedById(String createdById);
}
