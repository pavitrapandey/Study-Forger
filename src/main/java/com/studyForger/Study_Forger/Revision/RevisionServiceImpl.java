package com.studyForger.Study_Forger.Revision;

import com.studyForger.Study_Forger.Exception.InvalidInputException;
import com.studyForger.Study_Forger.Exception.NotFoundException;
import com.studyForger.Study_Forger.Topic.Topic;
import com.studyForger.Study_Forger.Topic.TopicRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

@Service
public class RevisionServiceImpl implements RevisionService{

    @Autowired
    private RevisionRepository revisionRepository;
    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private ModelMapper modelMapper;

    Logger logger= LoggerFactory.getLogger(RevisionServiceImpl.class);

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    @Override
    @Transactional
    public RevisionResponseDto reviewTopic(String topicId, int qualityScore) {
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new NotFoundException("Topic doesn't exist please check the input: " + topicId));

        if (topic.isCompleted()) {
            throw new InvalidInputException("Topic already mastered ");
        }

        // Validate quality score
        if (qualityScore < 0 || qualityScore > 5) {
            throw new InvalidInputException("Quality score must be between 0 and 5");
        }

        LocalDate now = LocalDate.now();
        boolean shouldComplete=false;


        List<Revision> lastRevisions = revisionRepository.findTop2ByTopicIdOrderByLastReviewDateDesc(topicId);
        if (qualityScore == 5 &&
                topic.getQualityScore()==5){
            // Mark topic complete directly without further SM-2 update
            shouldComplete = true;
            topic.setCompleted(shouldComplete);
            topic.setNextReviewDate(null);
            topic.setLastReviewDate(now);
            topic.setQualityScore(qualityScore);
            topic.setUpdatedAt(now);
            topic.setHaveRevised(true);
            topicRepository.save(topic);

            // Save only this final revision entry
            Revision revision = Revision.builder()
                    .id(UUID.randomUUID().toString())
                    .topic(topic)
                    .repetition(topic.getRepetition())
                    .interval(topic.getInterval())
                    .easeFactor(topic.getEaseFactor())
                    .lastReviewDate(now)
                    .qualityScore(qualityScore)
                    .nextReviewDate(null)
                    .build();
            revisionRepository.save(revision);

            topicRepository.delete(topic);

            logger.info("Topic {} mastered with two consecutive 5's.", topic.getTopicName());

            return RevisionResponseDto.builder()
                    .easeFactor(topic.getEaseFactor())
                    .repetition(topic.getRepetition())
                    .interval(topic.getInterval())
                    .lastReviewDate(now)
                    .nextReviewDate(null)
                    .topic(topic.getTopicName())
                    .lastQualityScore(qualityScore)
                    .build();
        }

        // ===== SM-2 Algorithm =====
        double easinessFactor = topic.getEaseFactor();
        int interval = topic.getInterval();
        int repetition = topic.getRepetition();

        // Difficulty adjustment
        switch (topic.getDifficulty()) {
            case EASY: easinessFactor += 0.1; break;
            case MEDIUM: easinessFactor += 0.05; break;
            case HARD: easinessFactor -= 0.1; break;
        }

        if (qualityScore >= 3) {
            if (repetition == 0) interval = 1;
            else if (repetition == 1) interval = 6;
            else interval = (int) Math.ceil(interval * easinessFactor);
            repetition++;

            easinessFactor += (0.1 - (5 - qualityScore) * (0.08 + (5 - qualityScore) * 0.02));
            if (easinessFactor < 1.3) easinessFactor = 1.3;
        } else {
            repetition = 0;
            interval = 1; // EF unchanged
        }

        LocalDate nextReviewDate = now.plusDays(interval);

        // Save this revision
        Revision revision = Revision.builder()
                .id(UUID.randomUUID().toString())
                .topic(topic)
                .repetition(repetition)
                .interval(interval)
                .easeFactor(easinessFactor)
                .lastReviewDate(now)
                .qualityScore(qualityScore)
                .nextReviewDate(nextReviewDate)
                .build();
        revisionRepository.save(revision);

        // Update topic
        topic.setEaseFactor(easinessFactor);
        topic.setRepetition(repetition);
        topic.setInterval(interval);
        topic.setLastReviewDate(now);
        topic.setQualityScore(qualityScore);
        topic.setNextReviewDate(nextReviewDate);
        topic.setUpdatedAt(now);
        topic.setHaveRevised(true);
        topicRepository.save(topic);

        logger.info("Next Review Date: {}", nextReviewDate);

        return RevisionResponseDto.builder()
                .easeFactor(easinessFactor)
                .repetition(repetition)
                .interval(interval)
                .lastReviewDate(now)
                .nextReviewDate(nextReviewDate)
                .topic(topic.getTopicName())
                .lastQualityScore(qualityScore)
                .build();

    }

    @Override
    @Transactional
    public List<RevisionTopicDto> dueTopics(String userId) {

        if (userId == null || userId.isEmpty()) {
            throw new NotFoundException("User ID cannot be null or empty");
        }
        // Get today's date
        LocalDate today = LocalDate.now();
        // Debug: Check if user has any topics at all
        List<Topic> allUserTopics = topicRepository.findByUserId(userId);
        System.out.println("Total topics for user " + userId + ": " + allUserTopics.size());

        // Debug: Check topics with nextReviewDate
        long topicsWithReviewDate = allUserTopics.stream()
                .filter(topic -> topic.getNextReviewDate() != null)
                .count();
        System.out.println("Topics with nextReviewDate: " + topicsWithReviewDate);

        // Debug: Check topics due today or before
        long topicsDueToday = allUserTopics.stream()
                .filter(topic -> topic.getNextReviewDate() != null &&
                        (topic.getNextReviewDate().isBefore(today) || topic.getNextReviewDate().isEqual(today)))
                .count();
        System.out.println("Topics due today or before: " + topicsDueToday);
        // Fetch topics that are due for review
        List<Topic> dueTopics=topicRepository.findDueTopicsIncludingToday(userId, today);
        if (dueTopics.isEmpty()) {
            throw new NotFoundException("No due topics found for user with ID: " + userId);
        }
        // Convert List<Topic> to List<TopicResponseDto>
        // Return the list of due topics
        return dueTopics.stream()
                .map(topic -> RevisionTopicDto.builder()
                        .topicName(topic.getTopicName())
                        .description(topic.getDescription())
                        .priority(topic.getPriority().name())
                        .difficulty(topic.getDifficulty().name())
                        .subject(topic.getSubject() != null ? topic.getSubject().getSubjectName() : null)
                        .lastReviewDate(topic.getLastReviewDate() != null ?
                                DATE_FORMAT.format(topic.getLastReviewDate()) : null)
                        .nextReviewDate(topic.getNextReviewDate() != null ?
                                DATE_FORMAT.format(topic.getNextReviewDate()) : null)
                        .revisionCount(topic.getRepetition())
                        .easeFactor(topic.getEaseFactor())
                        .build())
                .toList();
    }

    @Override
    public List<RevisionTopicDto> getAllTopicsByUserId(String userId){
    if (userId == null || userId.isEmpty()) {
        throw new NotFoundException("User ID cannot be null or empty");
    }
        // Fetch all topics for the user
        List<Topic> topics = topicRepository.findByUserId(userId);
        if (topics.isEmpty()) {
            throw new NotFoundException("No topics found for user with ID: " + userId);
        }
        // Convert List<Topic> to List<RevisionTopicDto>
        return topics.stream()
                .map(topic -> RevisionTopicDto.builder()
                        .topicName(topic.getTopicName())
                        .description(topic.getDescription())
                        .priority(topic.getPriority().name())
                        .difficulty(topic.getDifficulty().name())
                        .subject(topic.getSubject() != null ? topic.getSubject().getSubjectName() : null)
                        .lastReviewDate(topic.getLastReviewDate() != null ?
                                DATE_FORMAT.format(topic.getLastReviewDate()) : null)
                        .nextReviewDate(topic.getNextReviewDate() != null ?
                                DATE_FORMAT.format(topic.getNextReviewDate()) : null)
                        .revisionCount(topic.getRepetition())
                        .easeFactor(topic.getEaseFactor())
                        .build())
                .toList();
    }
}
