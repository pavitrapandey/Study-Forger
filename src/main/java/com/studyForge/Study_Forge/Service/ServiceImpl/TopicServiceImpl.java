package com.studyForge.Study_Forge.Service.ServiceImpl;

import com.studyForge.Study_Forge.Dto.TopicRequestDto;
import com.studyForge.Study_Forge.Dto.TopicResponseDto;
import com.studyForge.Study_Forge.Entity.Subject;
import com.studyForge.Study_Forge.Entity.Topic;
import com.studyForge.Study_Forge.Repository.SubjectRepository;
import com.studyForge.Study_Forge.Repository.TopicRepository;
import com.studyForge.Study_Forge.Service.TopicService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class TopicServiceImpl implements TopicService{


    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private ModelMapper modelMapper;

    private static final SimpleDateFormat DATE_FORMAT =
            new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");


    @Override
    public TopicResponseDto createTopic(TopicRequestDto request, String subjectId){
        // Validate subject existence
        if (!subjectRepository.existsById(subjectId)) {
            throw new IllegalArgumentException("Subject with ID " + subjectId + " does not exist.");
        }
        // Convert DTO to Entity
        Topic topic = dtoToEntity(request);
        topic=Topic.builder().
                topicName(request.getTopicName())
                .createdAt(new Date())
                .updatedAt(new Date())
                .description(request.getDescription())
                .priority(Topic.Priority.valueOf(request.getPriority()))
                .difficulty(Topic.Difficulty.valueOf(request.getDifficulty()))
                .build();

        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject with ID " + subjectId + " does not exist."));

        // Set the subject
      topic.setSubject(subject); // Save the topic
        Topic savedTopic = topicRepository.save(topic);
        // Convert Entity to DTO
        TopicResponseDto response = entityToDto(savedTopic);
        response = TopicResponseDto.builder()
                .id(savedTopic.getId())
                .topicName(savedTopic.getTopicName())
                .description(savedTopic.getDescription())
                .createdAt(DATE_FORMAT.format(savedTopic.getCreatedAt()))
                .updatedAt(DATE_FORMAT.format(savedTopic.getUpdatedAt()))
                .priority(savedTopic.getPriority().name())
                .difficulty(savedTopic.getDifficulty().name())
                .subject(subject.getSubjectName())
                .build();
        // Return the response DTO
        return response;
    }

    @Override
    public TopicResponseDto updateTopic(String topicId, TopicRequestDto request, String subjectId){
        // Validate subject existence
        if (!subjectRepository.existsById(subjectId)) {
            throw new IllegalArgumentException("Subject with ID " + subjectId + " does not exist.");
        }
        // Find the topic by ID
        Topic topic = topicRepository.findById(topicId)
                .orElseThrow(() -> new IllegalArgumentException("Topic with ID " + topicId + " does not exist."));
        // Update topic fields
        topic.setTopicName(request.getTopicName());
        topic.setDescription(request.getDescription());
        topic.setPriority(Topic.Priority.valueOf(request.getPriority()));
        topic.setDifficulty(Topic.Difficulty.valueOf(request.getDifficulty()));
        topic.setUpdatedAt(new Date());
        topic.setHave_revised(request.isHave_revised());
        // Set the subject
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new IllegalArgumentException("Subject with ID " + subjectId + " does not exist."));
        topic.setSubject(subject);
        // Save the updated topic
        Topic updatedTopic = topicRepository.save(topic);
        // Convert Entity to DTO
        return entityToDto(updatedTopic);
    }

    @Override
    public TopicResponseDto getTopicById(String topicId) {
        return null;
    }

    @Override
    public TopicResponseDto getAllTopicsBySubjectId(String subjectId) {
        return null;
    }

    @Override
    public void deleteTopic(String topicId, String subjectId) {

    }

    @Override
    public TopicResponseDto searchTopicByName(String topicName, String subjectId) {
        return null;
    }

    @Override
    public TopicResponseDto searchTopicByDifficulty(String difficulty, String subjectId) {
        return null;
    }


    private TopicResponseDto entityToDto(Topic topic) {
        return modelMapper.map(topic, TopicResponseDto.class);
    }

    private Topic dtoToEntity(TopicRequestDto topicResponseDto) {
        return modelMapper.map(topicResponseDto, Topic.class);
    }
}
