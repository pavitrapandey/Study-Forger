package com.studyForge.Study_Forge.Revision;

import com.studyForge.Study_Forge.Topic.Topic;
import com.studyForge.Study_Forge.Topic.TopicResponseDto;

import java.time.LocalDateTime;
import java.util.List;

public interface RevisionService{
    //review the topic
    RevisionResponseDto reviewTopic(String topicId, int qualityScore);

    //get all topics using the today date
    List<TopicResponseDto> dueTopics(String userId);

}
