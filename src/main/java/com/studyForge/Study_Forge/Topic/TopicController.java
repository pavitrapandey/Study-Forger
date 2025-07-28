package com.studyForge.Study_Forge.Topic;

import com.studyForge.Study_Forge.Dto.PageableRespond;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/subject/{subject_id}/topics")
public class TopicController{

    @Autowired
    private TopicService topicService;

    @PostMapping
    public ResponseEntity<TopicResponseDto> createTopic(
            @RequestBody TopicRequestDto request,
            @PathVariable("subject_id") String subjectId
            ){
        TopicResponseDto createdTopic = topicService.createTopic(request, subjectId);
        return new ResponseEntity<>(createdTopic, HttpStatus.CREATED);
    }

    @PutMapping("/{topic_id}")
    public ResponseEntity<TopicResponseDto> updateTopic(
            @PathVariable String topic_id,
            @RequestBody TopicRequestDto request,
            @PathVariable("subject_id") String subjectId
    ) {
        TopicResponseDto updatedTopic = topicService.updateTopic(topic_id, request, subjectId);
        return new ResponseEntity<>(updatedTopic, HttpStatus.OK);
    }

    @GetMapping("/{topic_id}")
    public ResponseEntity<TopicResponseDto> getTopic(
            @PathVariable String topic_id,
            @PathVariable("subject_id") String subjectId
    ){
        TopicResponseDto response=topicService.getTopicById(topic_id,subjectId);
        return new ResponseEntity<>(response,HttpStatus.FOUND);
    }

    @GetMapping
    public ResponseEntity<PageableRespond<TopicResponseDto>> getAllTopicsBySubject(
            @PathVariable("subject_id") String subjectId,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
       @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
    @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
    @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ){
        PageableRespond<TopicResponseDto> responses=topicService.getAllTopicsBySubjectId(subjectId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(responses,HttpStatus.FOUND);
    }


    @GetMapping("/search")
    public ResponseEntity<PageableRespond<TopicResponseDto>> searchTopicsByName(
            @PathVariable("subject_id") String subjectId,
            @RequestParam String topicName,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    )
    {
        PageableRespond<TopicResponseDto> responses=topicService.searchTopicByName(topicName,subjectId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(responses,HttpStatus.FOUND);
    }

    @GetMapping("/difficulty")
    public ResponseEntity<PageableRespond<TopicResponseDto>> searchTopicsByDifficulty(
            @PathVariable("subject_id") String subjectId,
            @RequestParam String difficulty,
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
            )
        {
            PageableRespond<TopicResponseDto> responses=topicService.searchTopicByDifficulty(difficulty,subjectId, pageNumber, pageSize, sortBy, sortDir);
            return new ResponseEntity<>(responses,HttpStatus.FOUND);
        }

        @DeleteMapping("/{topic_id}")
    public ResponseEntity<Void> deleteTopic(
            @PathVariable String topic_id,
            @PathVariable("subject_id") String subjectId
            ){
        topicService.deleteTopic(topic_id,subjectId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
}
