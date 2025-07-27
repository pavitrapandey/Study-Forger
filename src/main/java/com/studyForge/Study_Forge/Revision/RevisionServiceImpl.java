package com.studyForge.Study_Forge.Revision;

import com.studyForge.Study_Forge.Exception.InvalidInputException;
import com.studyForge.Study_Forge.Exception.NotFoundException;
import com.studyForge.Study_Forge.Topic.Topic;
import com.studyForge.Study_Forge.Topic.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RevisionServiceImpl implements RevisionService{

    @Autowired
    private RevisionRepository revisionRepository;
    @Autowired
    private TopicRepository topicRepository;

    @Override
    public RevisionResponseDto reviewTopic(String topicId, int qualityScore){
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(()->new NotFoundException("Topic doesn't exist please check the input: " + topicId));
        // Validate quality score
        if (qualityScore < 0 || qualityScore > 5) {
            System.out.println(HttpStatus.BAD_REQUEST);
            throw new InvalidInputException("Quality score must be between 0 and 5");
        }
        //SM-2 Algorithm
        double easinessFactor = topic.getEaseFactor();
        int interval = topic.getInterval();
        int repetition = topic.getRepetition();

       //switchCase for difficulty level
        switch (topic.getDifficulty()) {
            case EASY:
                easinessFactor += 0.1;
                break;
            case MEDIUM:
                easinessFactor += 0.05;
                break;
            case HARD:
                easinessFactor -= 0.1;
                break;
        }
        // Update easiness factor based on quality score
        if (qualityScore >= 3) {
            if (repetition == 0) {
                interval = 1;
            } else if (repetition == 1) {
                interval = 6;
            } else {
                interval = (int) Math.ceil(interval * easinessFactor);
            }

            repetition++;

            // Update EF
            easinessFactor = easinessFactor + (0.1 - (5 - qualityScore) * (0.08 + (5 - qualityScore) * 0.02));
            if (easinessFactor < 1.3) easinessFactor = 1.3;
        } else {
            repetition = 0;
            interval = 1;
            // EF unchanged
        }
        LocalDateTime now= LocalDateTime.now();
        LocalDateTime nextReviewDate = now.plusDays(interval);

        Revision revision = Revision.builder()
                .id(UUID.randomUUID().toString())
                .topic(topic)
                .repetition(repetition)
                .interval(interval)
                .easeFactor(easinessFactor)
                .lastReviewDate(now)
                .nextReviewDate(nextReviewDate)
                .build();
        // Save the revision
        revisionRepository.save(revision);

        // Update topic with new values
        topic.setEaseFactor(easinessFactor);
        topic.setRepetition(repetition);
        topic.setInterval(interval);
        topic.setLastReviewDate(now);
        topic.setNextReviewDate(nextReviewDate);
        topic.setUpdatedAt(nextReviewDate);
        topic.setHave_revised(true);

        // Save the updated topic
        topicRepository.save(topic);

        //return revision response
        return RevisionResponseDto.builder()
                .easeFactor(easinessFactor)
                .repetition(repetition)
                .interval(interval)
                .lastReviewDate(LocalDateTime.now())
                .nextReviewDate(LocalDateTime.now().plusDays(interval))
                .topic(topic.getTopicName())
                .lastQualityScore(qualityScore)
                .build();
    }

    @Override
    public List<Topic> dueTopics(String userId) {

        if (userId == null || userId.isEmpty()) {
            throw new NotFoundException("User ID cannot be null or empty");
        }

        List<Topic> topics= topicRepository.findByUserId(userId);
        // Check if user exists
        if (!topics.isEmpty()) {
            return topics.stream()
                    .filter(t -> t.getLastReviewDate() != null &&
                            t.getLastReviewDate().plusDays(t.getInterval()).isBefore(LocalDateTime.now()))
                    .collect(Collectors.toList());
             }
        //Set Today
        return List.of();
    }
}
