package com.studyForge.Study_Forge.Controller;

import com.studyForge.Study_Forge.Dto.TopicRequestDto;
import com.studyForge.Study_Forge.Dto.TopicResponseDto;
import com.studyForge.Study_Forge.Service.TopicService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
