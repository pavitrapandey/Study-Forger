package com.studyForger.Study_Forger.Revision;

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
@RequestMapping("/api/revision")
public class RevisionController {

    @Autowired
    private RevisionService revisionService;

    @PutMapping
    @Operation(method = "PUT",description = "Review a topic")
    @ApiResponses(value={
            @ApiResponse(description = "Topic reviewed successfully", responseCode = "200"),
            @ApiResponse(description = "Topic not found", responseCode = "404")
    })
    public ResponseEntity<RevisionResponseDto> reviewTopic(@RequestBody RevisionRequestDto request){
        RevisionResponseDto response = revisionService.reviewTopic(request.getTopicId(),request.getQualityScore());
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("due/{userId}")
    @Operation(method = "GET",description = "Get due topics for a user")
    @ApiResponses(value={
            @ApiResponse(description = "Due topics found", responseCode = "200"),
            @ApiResponse(description = "No due topics found", responseCode = "404")
    })
    public ResponseEntity<List<RevisionTopicDto>> getDueTopics(
            @PathVariable("userId") String userId
    ) {
       List<RevisionTopicDto> dueTopics = revisionService.dueTopics(userId);
        return new ResponseEntity<>(dueTopics, HttpStatus.OK);
    }

    @GetMapping("all/{userId}")
    @Operation(method = "GET",description = "Get all topics by user id")
    @ApiResponses(value={
            @ApiResponse(description = "Topics found", responseCode = "200"),
            @ApiResponse(description = "No topics found", responseCode = "404")
    })
    public ResponseEntity<List<RevisionTopicDto>> getAllTopicsByUserId(
            @PathVariable("userId") String userId
    ) {
        List<RevisionTopicDto> allTopics = revisionService.getAllTopicsByUserId(userId);
        return new ResponseEntity<>(allTopics, HttpStatus.OK);
    }

}
