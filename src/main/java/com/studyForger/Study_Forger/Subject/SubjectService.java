package com.studyForger.Study_Forger.Subject;

import com.studyForger.Study_Forger.Dto.PageableRespond;

public interface SubjectService {
    // Method to create a new subject
    SubjectDto createSubject(SubjectDto subjectDto,String userId);
    // Method to update an existing subject
    SubjectDto updateSubject(String subjectId, SubjectDto subjectDto);
    // Method to get a subject by its ID
    SubjectDto getSubjectById(String subjectId);
    // Method to get all subjects of User
    PageableRespond<SubjectDto> getAllSubjectsByUserId(String userId, int page, int size,
                                                       String sortBy, String sortDir);

    // Method to delete a subject by its ID
    void deleteSubject(String subjectId);

    //Search subject by name
    PageableRespond<SubjectDto> searchSubjectByName(String subjectName, String userId, int page, int size,String sortBy, String sortDir);

    Subject findSubjectById(String id);

}
