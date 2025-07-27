package com.studyForge.Study_Forge.Revision;

import com.studyForge.Study_Forge.Topic.Topic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/revision")
public class RevisionController {

    @Autowired
    private RevisionService revisionService;

    @PutMapping
    public ResponseEntity<RevisionResponseDto> reviewTopic(@RequestBody RevisionRequestDto request){
        RevisionResponseDto response = revisionService.reviewTopic(request.getTopicId(),request.getQualityScore());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("due/{userId}")
    public ResponseEntity<List<Topic>> getDueTopics(@PathVariable String userId) {
        List<Topic> dueTopics = revisionService.dueTopics(userId);
        return new ResponseEntity<>(dueTopics, HttpStatus.OK);
    }

}
