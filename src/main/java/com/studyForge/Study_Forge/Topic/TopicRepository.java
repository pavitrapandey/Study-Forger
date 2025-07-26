package com.studyForge.Study_Forge.Topic;

import com.studyForge.Study_Forge.Subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, String>{
    List<Topic> findBySubjectId(String subjectId);

    List<Topic> findByTopicNameAndSubject(String topicName,Subject subject);

    List<Topic> findByDifficultyAndSubject(Topic.Difficulty difficulty,Subject subject);
}