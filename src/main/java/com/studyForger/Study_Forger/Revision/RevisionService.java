package com.studyForger.Study_Forger.Revision;

import com.studyForger.Study_Forger.Dto.PageableRespond;
import com.studyForger.Study_Forger.Topic.TopicResponseDto;

public interface RevisionService{
    //review the topic
    String reviewTopic(String topicId, int qualityScore);

    //get all topics using the today date
    PageableRespond<TopicResponseDto> dueTopics(String userId, int pageNumber, int pageSize, String sortBy, String sortDir);

}
