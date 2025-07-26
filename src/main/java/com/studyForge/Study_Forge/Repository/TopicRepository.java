package com.studyForge.Study_Forge.Repository;

import com.studyForge.Study_Forge.Entity.Subject;
import com.studyForge.Study_Forge.Entity.Topic;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, String>{
    List<Topic> findBySubjectId(String subjectId);

    List<Topic> findByTopicNameAndSubject(String topicName,Subject subject);

    List<Topic> findByDifficultyAndSubject(Topic.Difficulty difficulty,Subject subject);
}