package com.studyForger.Study_Forger.Topic;


import com.studyForger.Study_Forger.Dto.PageableRespond;

import java.util.List;

public interface TopicService {
    //create
    TopicResponseDto createTopic(TopicRequestDto request, String subjectId);
    //update
    TopicResponseDto updateTopic(String topicId, TopicRequestDto request, String subjectId);
    //get by id
    TopicResponseDto getTopicById(String topicId,String subjectId);
    //get all topics by subject id
    PageableRespond<TopicResponseDto> getAllTopicsBySubjectId(String subjectId,int size,int number,String sortBy,String sortDir);
    //delete topic by id
    void deleteTopic(String topicId, String subjectId);
    //search topic by name
    PageableRespond<TopicResponseDto> searchTopicByName(String topicName, String subjectId,int size,int number,String sortBy,String sortDir);
    //search topic by difficulty
    PageableRespond<TopicResponseDto> searchTopicByDifficulty(String difficulty, String subjectId,int size,int number,String sortBy,String sortDir);
    //Find By UserId
   List<TopicResponseDto> findByUserId(String userId);


    Topic findTopicById(String id);
    List<Topic> findAll(String id);
}
