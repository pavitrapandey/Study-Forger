package com.studyForger.Study_Forger.Topic;

import com.studyForger.Study_Forger.Subject.Subject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface TopicRepository extends JpaRepository<Topic, String>{
    Page<Topic> findBySubjectId(String subjectId, Pageable pageable);

    List<Topic> findBySubject(Subject subject);

    Page<Topic> findByTopicNameAndSubject(String topicName, Subject subject, Pageable pageable);

    Page<Topic> findByDifficultyAndSubject(Topic.Difficulty difficulty,Subject subject, Pageable pageable);

    @Query("""
    SELECT COUNT(t) 
    FROM Topic t 
    WHERE t.subject.createdBy.id = :userId 
      AND t.nextReviewDate = :today
""")
    int countTopicsDueToday(@Param("userId") String userId, @Param("today") LocalDate today);


    @Query("SELECT COUNT(t) FROM Topic t WHERE t.subject.createdBy.id = :userId AND t.isCompleted = false AND t.createdAt = :today")
    int countByUserIdCreatedToday(@Param("userId") String userId, @Param("today") LocalDate today);

    @Query("SELECT COUNT(t) FROM Topic t WHERE t.subject.createdBy.id = :userId AND t.haveRevised = true AND t.nextReviewDate = :today")
    int countByUserIdHaveRevisedToday(@Param("userId") String userId, @Param("today") LocalDate today);

    @Query("SELECT t FROM Topic t WHERE t.subject.createdBy.id = :userId")
    Page<Topic> findByUserId(@Param("userId") String userId,
                             Pageable pageable);


}