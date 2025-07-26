package com.studyForge.Study_Forge.Subject;

import java.util.List;

public interface SubjectService {
    // Method to create a new subject
    SubjectDto createSubject(SubjectDto subjectDto,String userId);
    // Method to update an existing subject
    SubjectDto updateSubject(String subjectId, SubjectDto subjectDto);
    // Method to get a subject by its ID
    SubjectDto getSubjectById(String subjectId);
    // Method to get all subjects of User
    List<SubjectDto> getAllSubjectsByUserId(String userId);

    // Method to delete a subject by its ID
    void deleteSubject(String subjectId);

    //Search subject by name
    List<SubjectDto> searchSubjectByName(String subjectName, String userId);

    Subject findSubjectById(String id);
}
