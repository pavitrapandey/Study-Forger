package com.studyForger.Study_Forger.Revision;

import com.studyForger.Study_Forger.Dto.PageableRespond;
import com.studyForger.Study_Forger.Exception.InvalidInputException;
import com.studyForger.Study_Forger.Exception.NotFoundException;
import com.studyForger.Study_Forger.Helper.helper;
import com.studyForger.Study_Forger.Topic.Topic;
import com.studyForger.Study_Forger.Topic.TopicRepository;
import com.studyForger.Study_Forger.Topic.TopicResponseDto;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
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

    @Override
    @Transactional
    public String reviewTopic(String topicId, int qualityScore){
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(()->new NotFoundException("Topic doesn't exist please check the input: " + topicId));
        // Validate quality score
        if (qualityScore < 0 || qualityScore > 5) {
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
        LocalDate now = LocalDate.now();
        LocalDate nextReviewDate = now.plusDays(interval);

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
        // Save the revision
        revisionRepository.save(revision);

        // Update topic with new values
        topic.setEaseFactor(easinessFactor);
        topic.setRepetition(repetition);
        topic.setInterval(interval);
        topic.setLastReviewDate(now);
        topic.setNextReviewDate(nextReviewDate);
        topic.setUpdatedAt(now); // updatedAt should be the current time of the update
        topic.setHaveRevised(true);

        List<Revision> lastRevisions = revisionRepository.findTop2ByTopicIdOrderByLastReviewDateDesc(topicId);

        boolean twoPerfect = lastRevisions.size() == 2 &&
                lastRevisions.get(0).getQualityScore() == 5 &&
                lastRevisions.get(1).getQualityScore() == 5;

        if (twoPerfect) {
            topic.setCompleted(true);
            topic.setNextReviewDate(null); // Optional: stop further reviews
        }

        // Save the updated topic
        topicRepository.save(topic);

        //return revision response
        return RevisionResponseDto.builder()
                .easeFactor(easinessFactor)
                .repetition(repetition)
                .interval(interval)
                .lastReviewDate(now)
                .nextReviewDate(nextReviewDate)
                .topic(topic.getTopicName())
                .lastQualityScore(qualityScore)
                .build().toString();
    }

    @Override
    public PageableRespond<TopicResponseDto> dueTopics(String userId, int pageNumber, int pageSize, String sortBy, String sortDir) {

        if (userId == null || userId.isEmpty()) {
            throw new NotFoundException("User ID cannot be null or empty");
        }

        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);

        Page<Topic> topics = topicRepository.findByUserId(userId,pageable);

        if (topics == null || topics.isEmpty()) {
            throw new NotFoundException("No topics found for user with ID: " + userId);
        }
        LocalDate today = LocalDate.now();
        List<Topic> dueTopics = topics.stream()
                .filter(topic -> topic.getNextReviewDate() != null && topic.getNextReviewDate().isBefore(today))
                .toList();

        if (dueTopics.isEmpty()) {
            throw new NotFoundException("No due topics found for user with ID: " + userId);
        }
        List<TopicResponseDto> allDueTopics= dueTopics.stream()
                .map(topic -> modelMapper.map(topic, TopicResponseDto.class))
                .toList();

        return helper.getPageableResponse(topics, TopicResponseDto.class);
    }
}
