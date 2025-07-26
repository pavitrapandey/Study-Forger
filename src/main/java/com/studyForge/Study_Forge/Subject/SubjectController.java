package com.studyForge.Study_Forge.Subject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users/{user_id}/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    public ResponseEntity<SubjectDto> createSubject(
            @RequestBody SubjectDto subjectDto,
            @PathVariable("user_id") String userId)
             {
        SubjectDto createdSubject = subjectService.createSubject(subjectDto, userId);
        return new ResponseEntity<>(createdSubject, HttpStatus.CREATED);
    }

    @PutMapping("/{subject_id}")
    public ResponseEntity<SubjectDto> updateSubject(
            @PathVariable String subject_id,
            @RequestBody SubjectDto subjectDto,
            @PathVariable("user_id") String userId
    ) {
        //Check if this user is the creator of the subject
        if (!subjectService.getSubjectById(subject_id).getCreatedBy().getId().equals(userId)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        SubjectDto updatedSubject = subjectService.updateSubject(subject_id, subjectDto);
        return new ResponseEntity<>(updatedSubject, HttpStatus.OK);
    }

    @GetMapping("/{subject_id}")
    public ResponseEntity<SubjectDto> getSubjectById(@PathVariable String subject_id,
                                                     @PathVariable("user_id") String userId
    ) {

        SubjectDto subject = subjectService.getSubjectById(subject_id);
        return new ResponseEntity<>(subject, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<SubjectDto>> getAllSubjectsByUserId(@PathVariable String user_id) {
        List<SubjectDto> subjects = subjectService.getAllSubjectsByUserId(user_id);
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @DeleteMapping("/{subject_id}")
    public ResponseEntity<Void> deleteSubject(@PathVariable String subject_id,
                                                @PathVariable("user_id") String userId
    ) {
        //Check if this user is the creator of the subject
        if (!subjectService.getSubjectById(subject_id).getCreatedBy().getId().equals(userId)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        subjectService.deleteSubject(subject_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    public ResponseEntity<List<SubjectDto> > searchSubjectByName(@RequestParam String subjectName
                                                            , @PathVariable("user_id") String userId
    ) {

        List<SubjectDto> subjects = subjectService.searchSubjectByName(subjectName, userId);
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }
}
