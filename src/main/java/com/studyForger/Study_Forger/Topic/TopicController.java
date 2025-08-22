package com.studyForger.Study_Forger.Topic;

import com.studyForger.Study_Forger.Dto.PageableRespond;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.studyForger.Study_Forger.Configuration.AppConstants;

import java.util.List;


@CrossOrigin(AppConstants.FRONT_END_URL)
@RestController
@RequestMapping("/api/")
public class TopicController{

    @Autowired
    private TopicService topicService;

    @PostMapping("subject/{subject_id}/topics")
    @Operation(method = "POST",description = "Create a new topic under a subject")
    @ApiResponses(value={
            @ApiResponse(description = "Topic created successfully", responseCode = "201"),
            @ApiResponse(description = "Topic already exists", responseCode = "400"),
            @ApiResponse(description = "Invalid request", responseCode = "400")
    })
    public ResponseEntity<TopicResponseDto> createTopic(
            @RequestBody TopicRequestDto request,
            @PathVariable("subject_id") String subjectId
            ){
        TopicResponseDto createdTopic = topicService.createTopic(request, subjectId);
        return new ResponseEntity<>(createdTopic, HttpStatus.CREATED);
    }

    @PutMapping("subject/{subject_id}/topics/{topic_id}")
    @Operation(method = "PUT",description = "Update an existing topic under a subject")
    @ApiResponses(value={
            @ApiResponse(description = "Topic updated successfully", responseCode = "200"),
            @ApiResponse(description = "Topic not found", responseCode = "404")
    })
    public ResponseEntity<TopicResponseDto> updateTopic(
            @PathVariable String topic_id,
            @RequestBody TopicRequestDto request,
            @PathVariable("subject_id") String subjectId
    ) {
        TopicResponseDto updatedTopic = topicService.updateTopic(topic_id, request, subjectId);
        return new ResponseEntity<>(updatedTopic, HttpStatus.OK);
    }

    @GetMapping("subject/{subject_id}/topics/{topic_id}")
    @Operation(method = "GET",description = "Get a topic by ID")
    @ApiResponses(value={
            @ApiResponse(description = "Topic found", responseCode = "200"),
            @ApiResponse(description = "Topic not found", responseCode = "404")
    })
    public ResponseEntity<TopicResponseDto> getTopic(
            @PathVariable String topic_id,
            @PathVariable("subject_id") String subjectId
    ){
        TopicResponseDto response=topicService.getTopicById(topic_id,subjectId);
        return new ResponseEntity<>(response,HttpStatus.FOUND);
    }

    @GetMapping("subject/{subject_id}/topics")
    @Operation(method = "GET",description = "Get all topics by subject id")
    @ApiResponses(value={
            @ApiResponse(description = "Topics found", responseCode = "200"),
            @ApiResponse(description = "No topics found", responseCode = "404")
    })
    public ResponseEntity<PageableRespond<TopicResponseDto>> getAllTopicsBySubject(
            @PathVariable("subject_id") String subjectId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
       @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
    @RequestParam(value = "sortBy", defaultValue = "topicName", required = false) String sortBy,
    @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        PageableRespond<TopicResponseDto> responses=topicService.getAllTopicsBySubjectId(subjectId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(responses,HttpStatus.FOUND);
    }


    @GetMapping("subject/{subject_id}/topics/search")
    @Operation(method = "GET",description = "Search topics by name")
    @ApiResponses(value={
            @ApiResponse(description = "Topics found", responseCode = "200"),
            @ApiResponse(description = "No topics found", responseCode = "404")
    })
    public ResponseEntity<PageableRespond<TopicResponseDto>> searchTopicsByName(
            @PathVariable("subject_id") String subjectId,
            @RequestParam String topicName,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "topicName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    )
    {
        PageableRespond<TopicResponseDto> responses=topicService.searchTopicByName(topicName,subjectId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(responses,HttpStatus.FOUND);
    }

    @GetMapping("subject/{subject_id}/topics/difficulty")
    @Operation(method = "GET",description = "Search topics by difficulty")
    @ApiResponses(value={
            @ApiResponse(description = "Topics found", responseCode = "200"),
            @ApiResponse(description = "No topics found", responseCode = "404")
    })
    public ResponseEntity<PageableRespond<TopicResponseDto>> searchTopicsByDifficulty(
            @PathVariable("subject_id") String subjectId,
            @RequestParam String difficulty,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "topicName", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
            )
        {
            PageableRespond<TopicResponseDto> responses=topicService.searchTopicByDifficulty(difficulty,subjectId, pageNumber, pageSize, sortBy, sortDir);
            return new ResponseEntity<>(responses,HttpStatus.FOUND);
        }

        @DeleteMapping("subject/{subject_id}/topics/{topic_id}")
        @Operation(method = "DELETE",description = "Delete a topic by ID")
        @ApiResponses(value={
            @ApiResponse(description = "Topic deleted successfully", responseCode = "204"),
            @ApiResponse(description = "Topic not found", responseCode = "404")
        })
    public ResponseEntity<Void> deleteTopic(
            @PathVariable String topic_id,
            @PathVariable("subject_id") String subjectId
            ){
        topicService.deleteTopic(topic_id,subjectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        @GetMapping("/user/{userId}/topic")
        @Operation(method = "GET",description = "Get all topics by user id")
        @ApiResponses(value={
            @ApiResponse(description = "Topics found", responseCode = "200"),
            @ApiResponse(description = "No topics found", responseCode = "404")
        })
    public ResponseEntity<List<TopicResponseDto>> getUserTopics(
            @PathVariable("userId") String userId
             ){
        List<TopicResponseDto> responses=topicService.findByUserId(userId);
        return new ResponseEntity<>(responses,HttpStatus.FOUND);
        }
}
