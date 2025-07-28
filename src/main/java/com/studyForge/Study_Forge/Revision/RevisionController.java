package com.studyForge.Study_Forge.Revision;

import com.studyForge.Study_Forge.Dto.PageableRespond;
import com.studyForge.Study_Forge.Topic.Topic;
import com.studyForge.Study_Forge.Topic.TopicResponseDto;
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
