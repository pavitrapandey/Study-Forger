package com.studyForger.Study_Forger.Revision;

import com.studyForger.Study_Forger.Dto.PageableRespond;
import com.studyForger.Study_Forger.Topic.TopicResponseDto;

import java.util.List;

public interface RevisionService{
    //review the topic
    RevisionResponseDto reviewTopic(String topicId, int qualityScore);

    //get all topics using the today date
    List<RevisionTopicDto> dueTopics(String userId);

    List<RevisionTopicDto> getAllTopicsByUserId(String userId);

}
