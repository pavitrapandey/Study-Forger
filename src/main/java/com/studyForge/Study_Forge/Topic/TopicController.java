package com.studyForge.Study_Forge.Topic;

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
    public ResponseEntity<List<TopicResponseDto>> getAllTopicsBySubject(
            @PathVariable("subject_id") String subjectId
    ){
        List<TopicResponseDto> responses=topicService.getAllTopicsBySubjectId(subjectId);
        return new ResponseEntity<>(responses,HttpStatus.FOUND);
    }


    @GetMapping("/search")
    public ResponseEntity<List<TopicResponseDto>> searchTopicsByName(
            @PathVariable("subject_id") String subjectId,
            @RequestParam String topicName
    )
    {
        List<TopicResponseDto> responses=topicService.searchTopicByName(topicName,subjectId);
        return new ResponseEntity<>(responses,HttpStatus.FOUND);
    }

    @GetMapping("/difficulty")
    public ResponseEntity<List<TopicResponseDto>> searchTopicsByDifficulty(
            @PathVariable("subject_id") String subjectId,
            @RequestParam String difficulty
            )
        {
            List<TopicResponseDto> responses=topicService.searchTopicByDifficulty(difficulty,subjectId);
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
