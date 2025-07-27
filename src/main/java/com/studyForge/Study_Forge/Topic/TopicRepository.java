package com.studyForge.Study_Forge.Topic;

import com.studyForge.Study_Forge.Subject.Subject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, String>{
    List<Topic> findBySubjectId(String subjectId);

    List<Topic> findByTopicNameAndSubject(String topicName,Subject subject);

    List<Topic> findByDifficultyAndSubject(Topic.Difficulty difficulty,Subject subject);

    List<Topic> findByUserId(String userId);

    @Query("SELECT t FROM Topic t LEFT JOIN FETCH t.user WHERE t.user.id = :userId AND t.nextReviewDate <= :currentDate")
    List<Topic> findDueTopicsForUser(@Param("userId") String userId, @Param("currentDate") LocalDateTime currentDate);
}