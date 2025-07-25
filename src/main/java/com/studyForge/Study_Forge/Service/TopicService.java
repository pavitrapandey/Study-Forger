package com.studyForge.Study_Forge.Service;


import com.studyForge.Study_Forge.Dto.TopicRequestDto;
import com.studyForge.Study_Forge.Dto.TopicResponseDto;

public interface TopicService {
    //create
    TopicResponseDto createTopic(TopicRequestDto request, String subjectId);
    //update
    TopicResponseDto updateTopic(String topicId, TopicRequestDto request, String subjectId);
    //get by id
    TopicResponseDto getTopicById(String topicId);
    //get all topics by subject id
    TopicResponseDto getAllTopicsBySubjectId(String subjectId);
    //delete topic by id
    void deleteTopic(String topicId, String subjectId);
    //search topic by name
    TopicResponseDto searchTopicByName(String topicName, String subjectId);
    //search topic by difficulty
    TopicResponseDto searchTopicByDifficulty(String difficulty, String subjectId);
}
