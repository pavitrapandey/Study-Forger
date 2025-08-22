package com.studyForger.Study_Forger.Subject;

import com.studyForger.Study_Forger.Dto.PageableRespond;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.studyForger.Study_Forger.Configuration.AppConstants;


@CrossOrigin(AppConstants.FRONT_END_URL)
@RestController
@RequestMapping("/api/users/{user_id}/subjects")
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping
    @Operation(method = "POST",description = "Create a new subject under a user")
    @ApiResponses(value={
            @ApiResponse(description = "Subject created successfully", responseCode = "201"),
            @ApiResponse(description = "Subject already exists", responseCode = "400"),
            @ApiResponse(description = "Invalid request", responseCode = "400")
    })
    public ResponseEntity<SubjectDto> createSubject(
            @RequestBody SubjectDto subjectDto,
            @PathVariable("user_id") String userId)
             {
        SubjectDto createdSubject = subjectService.createSubject(subjectDto, userId);
        return new ResponseEntity<>(createdSubject, HttpStatus.CREATED);
    }

    @PutMapping("/{subject_id}")
    @Operation(method = "PUT",description = "Update an existing subject under a user")
    @ApiResponses(value={
            @ApiResponse(description = "Subject updated successfully", responseCode = "200"),
            @ApiResponse(description = "Subject not found", responseCode = "404")
    })
    public ResponseEntity<SubjectDto> updateSubject(
            @PathVariable String subject_id,
            @RequestBody SubjectDto subjectDto,
            @PathVariable("user_id") String userId
    ) {
        //Check if this user is the creator of the subject
        if (!subjectService.getUserIdBySubjectId(subject_id).equals(userId)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        SubjectDto updatedSubject = subjectService.updateSubject(subject_id, subjectDto);
        return new ResponseEntity<>(updatedSubject, HttpStatus.OK);
    }

    @GetMapping("/{subject_id}")
    @Operation(method = "GET",description = "Get a subject by ID")
    @ApiResponses(value={
            @ApiResponse(description = "Subject found", responseCode = "200"),
            @ApiResponse(description = "Subject not found", responseCode = "404")
    })
    public ResponseEntity<SubjectDto> getSubjectById(@PathVariable String subject_id,
                                                     @PathVariable("user_id") String userId
    ) {

        SubjectDto subject = subjectService.getSubjectById(subject_id);
        return new ResponseEntity<>(subject, HttpStatus.OK);
    }

    @GetMapping
    @Operation(method = "GET",description = "Get all subjects by user id")
    @ApiResponses(value={
            @ApiResponse(description = "Subjects found", responseCode = "200"),
            @ApiResponse(description = "No subjects found", responseCode = "404")
    })
    public ResponseEntity<PageableRespond<SubjectDto>> getAllSubjectsByUserId(
            @PathVariable String user_id,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "subjectName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        PageableRespond<SubjectDto> subjects = subjectService.getAllSubjectsByUserId(user_id, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }

    @DeleteMapping("/{subject_id}")
    @Operation(method = "DELETE",description = "Delete a subject by ID")
    @ApiResponses(value={
            @ApiResponse(description = "Subject deleted successfully", responseCode = "204"),
            @ApiResponse(description = "Subject not found", responseCode = "404")
    })
    public ResponseEntity<Void> deleteSubject(@PathVariable String subject_id,
                                                @PathVariable("user_id") String userId
    ) {
        //Check if this user is the creator of the subject
         if (!subjectService.getUserIdBySubjectId(subject_id).equals(userId)){
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        subjectService.deleteSubject(subject_id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/search")
    @Operation(method = "GET",description = "Search subjects by name")
    @ApiResponses(value={
            @ApiResponse(description = "Subjects found", responseCode = "200"),
            @ApiResponse(description = "No subjects found", responseCode = "404")
    })
    public ResponseEntity<PageableRespond<SubjectDto>> searchSubjectByName(
            @RequestParam String subjectName,
            @PathVariable("user_id") String userId,
            @RequestParam(defaultValue = "0") int pageNumber,
            @RequestParam(defaultValue = "10") int pageSize,
            @RequestParam(defaultValue = "subjectName") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {

        PageableRespond<SubjectDto> subjects = subjectService.searchSubjectByName(subjectName, userId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(subjects, HttpStatus.OK);
    }
}
