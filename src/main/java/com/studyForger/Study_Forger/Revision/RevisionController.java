package com.studyForger.Study_Forger.Revision;

import com.studyForger.Study_Forger.Dto.PageableRespond;
import com.studyForger.Study_Forger.Topic.TopicResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.studyForger.Study_Forger.Configuration.AppConstants;

import java.util.List;


@CrossOrigin(AppConstants.FRONT_END_URL)
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
    public ResponseEntity<List<RevisionTopicDto>> getDueTopics(
            @PathVariable("userId") String userId
    ) {
       List<RevisionTopicDto> dueTopics = revisionService.dueTopics(userId);
        return new ResponseEntity<>(dueTopics, HttpStatus.OK);
    }

    @GetMapping("all/{userId}")
    public ResponseEntity<List<RevisionTopicDto>> getAllTopicsByUserId(
            @PathVariable("userId") String userId
    ) {
        List<RevisionTopicDto> allTopics = revisionService.getAllTopicsByUserId(userId);
        return new ResponseEntity<>(allTopics, HttpStatus.OK);
    }

}
