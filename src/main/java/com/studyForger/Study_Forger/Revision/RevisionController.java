package com.studyForger.Study_Forger.Revision;

import com.studyForger.Study_Forger.Dto.PageableRespond;
import com.studyForger.Study_Forger.Topic.TopicResponseDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/revision")
public class RevisionController {

    @Autowired
    private RevisionService revisionService;

    @PutMapping
    public ResponseEntity<String> reviewTopic(@RequestBody RevisionRequestDto request){
        String response = revisionService.reviewTopic(request.getTopicId(),request.getQualityScore()).toString();
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("due/{userId}")
    public ResponseEntity<PageableRespond<TopicResponseDto>> getDueTopics(
            @PathVariable String userId
            , @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "priority", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        PageableRespond<TopicResponseDto> dueTopics = revisionService.dueTopics(userId, pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(dueTopics, HttpStatus.OK);
    }

}
