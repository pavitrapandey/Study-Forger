package com.studyForger.Study_Forger.Revision;

import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RevisionRepository extends JpaRepository<Revision, String>{
    List<Revision> findByNextReviewDateBefore(LocalDateTime date);

    List<Revision> findTop2ByTopicIdOrderByLastReviewDateDesc(String topicId);
}