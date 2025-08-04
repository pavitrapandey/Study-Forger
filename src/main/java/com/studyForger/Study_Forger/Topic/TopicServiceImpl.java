package com.studyForger.Study_Forger.Topic;

import com.studyForger.Study_Forger.Dto.PageableRespond;
import com.studyForger.Study_Forger.Exception.ResourceNotFoundException;
import com.studyForger.Study_Forger.Helper.helper;
import com.studyForger.Study_Forger.Subject.Subject;
import com.studyForger.Study_Forger.Exception.BadApiRequest;
import com.studyForger.Study_Forger.Exception.NotFoundException;
import com.studyForger.Study_Forger.Subject.SubjectService;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;


@Slf4j
@Service
public class TopicServiceImpl implements TopicService{


    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private ModelMapper modelMapper;

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    Logger logger = LoggerFactory.getLogger(TopicServiceImpl.class);

    @Override
    public TopicResponseDto createTopic(TopicRequestDto request, String subjectId){

        Subject subject = subjectService.findSubjectById(subjectId);
        if(subject==null){
            logger.info("Subject not found!! Kindly add the subject or Check your Input");
            throw new NotFoundException("Subject not found!! Kindly add the subject or Check your Input");
        }
        // Convert DTO to Entity
        Topic topic=Topic.builder().
                topicName(request.getTopicName())
                .createdAt(LocalDate.now())
                .updatedAt(LocalDate.now())
                .description(request.getDescription())
                .easeFactor(2.5)
                .repetition(0)
                .interval(0)
                .priority(Topic.Priority.valueOf(request.getPriority()))
                .difficulty(Topic.Difficulty.valueOf(request.getDifficulty()))
                .build();

        // Set the subject
      topic.setSubject(subject); // Save the topic
        Topic savedTopic = topicRepository.save(topic);
        // Convert Entity to DTO
        // Return the response DTO
        return TopicResponseDto.builder()
                .id(savedTopic.getId())
                .topicName(savedTopic.getTopicName())
                .description(savedTopic.getDescription())
                .createdAt(DATE_FORMAT.format(savedTopic.getCreatedAt()))
                .updatedAt(DATE_FORMAT.format(savedTopic.getUpdatedAt()))
                .priority(savedTopic.getPriority().name())
                .difficulty(savedTopic.getDifficulty().name())
                .subject(subject.getSubjectName())
                .build();
    }

    @Override
    public TopicResponseDto updateTopic(String topicId, TopicRequestDto request, String subjectId){
        Subject subject=subjectService.findSubjectById(subjectId);
        if(subject==null){
            logger.info("Subject not found!! Kindly add the subject or Check your Input");
            throw new NotFoundException("Subject not found!! Kindly add the subject or Check your Input");
        }
        // Find the topic by ID
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Topic with ID " + topicId + " does not exist."));
        // Update topic fields
        topic.setTopicName(request.getTopicName());
        topic.setDescription(request.getDescription());
        topic.setPriority(Topic.Priority.valueOf(request.getPriority()));
        topic.setDifficulty(Topic.Difficulty.valueOf(request.getDifficulty()));
        topic.setUpdatedAt(LocalDate.now());
        topic.setHaveRevised(request.isHave_revised());
        // Set the subject
        topic.setSubject(subject);
        // Save the updated topic
        Topic updatedTopic = topicRepository.save(topic);
        // Convert Entity to DTO
        return entityToDto(updatedTopic);
    }

    @Override
    public TopicResponseDto getTopicById(String topicId,String subjectId){
        Subject subject=  subjectService.findSubjectById(subjectId);
        if(subject==null){
            logger.info("Topic not found!! Kindly add the topic or Check your Input");
            throw new NotFoundException("Topic not found!! Kindly add the topic or Check your Input");
        }
                //check that subject have this topic or not
        Topic topic=topicRepository.findById(topicId).
                orElseThrow(()->
                        new NotFoundException
                                ("Topic not found!! Kindly add this " +
                                        "topic or Check your Input"));
        if(!subject.getTopics().contains(topic)){
            throw new NotFoundException("Topic not found!! Kindly add this \" +\n" +
                    "                                        \"topic or Check your Input");
        }
        return entityToDto(topic);
    }

    @Override
    public PageableRespond<TopicResponseDto> getAllTopicsBySubjectId(String subjectId,int page, int size, String sortBy, String sortDir){
        if (page < 0 || size <= 0) {
            throw new BadApiRequest("Page number and size must be greater than zero");
        }
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(page, size, sort);
        Subject subject=  subjectService.findSubjectById(subjectId);
        if(subject==null){
            logger.info("Subject not found!! Kindly add the subject or Check your Input");
            throw new NotFoundException("Subject not found!! Kindly add the subject or Check your Input");
        }
        Page<Topic> topics=topicRepository.findBySubjectId(subjectId, pageable);
        if(topics.isEmpty()){
            throw new NotFoundException("No topics found");
        }else{
           List<TopicResponseDto> dto= topics.stream()
                   .map(this::entityToDto)
                   .toList();
        }

        return helper.getPageableResponse(topics, TopicResponseDto.class);
    }

    @Override
    public void deleteTopic(String topicId, String subjectId)
    {
        Subject subject=  subjectService.findSubjectById(subjectId);
        if(subject==null){
            logger.info("Subject not found!! Kindly add the subject or Check your Input");
            throw new NotFoundException("Subject not found!! Kindly add the subject or Check your Input");
        }
        //check that subject have this topic or not
        Topic topic=topicRepository.findById(topicId).
                orElseThrow(()->
                        new NotFoundException
                                ("Topic not found!! Kindly add this " +
                                        "topic or Check your Input"));
        if(!subject.getTopics().contains(topic)){
            logger.info("Topic not found!! Kindly add this \" +\n" +
                    "                                        \"topic or Check your Input");
            throw new NotFoundException("Topic not found!! Kindly add this \" +\n" +
                    "                                        \"topic or Check your Input");
        }
        topicRepository.deleteById(topicId);

    }

    @Override
    public PageableRespond<TopicResponseDto> searchTopicByName(String topicName, String subjectId,int page, int size, String sortBy, String sortDir){
        if (topicName == null || topicName.isEmpty()) {
            throw new BadApiRequest("Topic name cannot be null or empty");
        }
        if (subjectId == null || subjectId.isEmpty()) {
            throw new BadApiRequest("Subject ID cannot be null or empty");
        }
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(page, size, sort);
        //check that subject exist or not
        Subject subject=  subjectService.findSubjectById(subjectId);
        if(subject==null){
            logger.info("Subject not found!! Kindly add the subject or Check your Input!!!");
            throw new NotFoundException("Subject not found!! Kindly add the subject or Check your Input");
        }
        //check that subject have this topic or not
        Page<Topic> topics=topicRepository.findByTopicNameAndSubject(topicName,subject,pageable);
        if(topics.isEmpty()){
            throw new NotFoundException("No topics found");
        }else{
            List<TopicResponseDto> dto= topics.stream()
                    .map(this::entityToDto)
                    .toList();
        }

        return helper.getPageableResponse(topics, TopicResponseDto.class);
    }

    @Override
    public PageableRespond<TopicResponseDto> searchTopicByDifficulty(String difficulty, String subjectId,int page, int size, String sortBy, String sortDir){
        Subject subject=  subjectService.findSubjectById(subjectId);
        if(subject==null){
            logger.info("Subject not found!! Kindly add the subject or Check your Input");
            throw new NotFoundException("Subject not found!! Kindly add the subject or Check your Input");
        }
        Sort sort = (sortDir.equalsIgnoreCase("desc")) ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable= PageRequest.of(page, size, sort);
        Topic.Difficulty diff;
        try {
            diff = Topic.Difficulty.valueOf(difficulty.toUpperCase());
        } catch (Exception e){
            throw new BadApiRequest("Invalid difficulty! Use EASY, MEDIUM, HARD");
        }

        Page<Topic> topics=topicRepository.findByDifficultyAndSubject(diff,subject,pageable);
        if (!topics.isEmpty()){
            List<TopicResponseDto> dtos= topics.stream()
                    .map(this::entityToDto)
                    .toList();
        }
        return helper.getPageableResponse(topics, TopicResponseDto.class);
    }

    @Override
    public List<TopicResponseDto> findByUserId(String userId){
        if (userId == null || userId.isEmpty()) {
            throw new BadApiRequest("User ID cannot be null or empty");
        }
        List<Topic> topics = topicRepository.findByUserId(userId);
        if (topics.isEmpty()) {
            logger.info("No topics found for user with ID: " + userId);
            throw new NotFoundException("No topics found for user with ID: " + userId);
        }
        return topics.stream()
                .map(this::entityToDto)
                .toList();
    }


    private TopicResponseDto entityToDto(Topic topic) {
        return modelMapper.map(topic, TopicResponseDto.class);
    }

    private Topic dtoToEntity(TopicRequestDto topicResponseDto) {
        return modelMapper.map(topicResponseDto, Topic.class);
    }

    @Override
    public Topic findTopicById(String id){
        return topicRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Topic", "id", id));
    }

    @Override
    public List<Topic> findAll(String id){
    Subject subject = subjectService.findSubjectById(id);
    if (subject != null) {
            return topicRepository.findBySubject(subject);
        }
    logger.info("No topics found for subject with ID: " + id);
    throw new NotFoundException("No topics found for subject with ID: " + id);

    }
}
