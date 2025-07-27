package com.studyForge.Study_Forge.Subject;

import com.studyForge.Study_Forge.Exception.BadApiRequest;
import com.studyForge.Study_Forge.Exception.NotFoundException;
import com.studyForge.Study_Forge.Exception.ResourceNotFoundException;
import com.studyForge.Study_Forge.User.User;
import com.studyForge.Study_Forge.User.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class SubjectServiceImpl implements SubjectService
{

    @Autowired
    private SubjectRepository subjectRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public SubjectDto createSubject(SubjectDto subjectDto, String userId){
        String subjectId = UUID.randomUUID().toString();

        // 2. Fetch creator user from DTO's ID
        User user =userService.findUserById(userId);
        if(user == null){
            throw new NotFoundException("User not found with id: " + userId);
        }

        // 3. Convert DTO to Entity
        Subject subject = Subject.builder()
                .id(subjectId)
                .subjectName(subjectDto.getSubjectName())
                .description(subjectDto.getDescription())
                .createdBy(user)
                .build();

        // 4. Save
        Subject saved = subjectRepository.save(subject);

        // 5. Return DTO
        return entityToDto(saved);
    }

    @Override
    public SubjectDto updateSubject(String subjectId, SubjectDto subjectDto){
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new NotFoundException("Subject not found with id: " + subjectId));
        // Update subject fields
        subject.setSubjectName(subjectDto.getSubjectName());
        subject.setDescription(subjectDto.getDescription());
        subjectRepository.save(subject);
        return entityToDto(subject);
    }

    @Override
    public SubjectDto getSubjectById(String subjectId){
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new NotFoundException("Subject not found with id: " + subjectId));
        // Convert Subject entity to SubjectDto
        return entityToDto(subject);
    }

    @Override
    public List<SubjectDto> getAllSubjectsByUserId(String userId){
        User user = userService.findUserById(userId);
        if(user == null){
            throw new NotFoundException("User not found with id: " + userId);
        }
        List<Subject> subjects = subjectRepository.findByCreatedById(user.getId());
        if (subjects != null && !subjects.isEmpty()) {
            return subjects.stream()
                    .map(this::entityToDto)
                    .toList();
        }
         //return empty list
        return List.of();
    }

    @Override
    public void deleteSubject(String subjectId){
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new BadApiRequest("Subject not found with id: " + subjectId));
        subjectRepository.delete(subject);

    }

    @Override
    public List<SubjectDto> searchSubjectByName(String subjectName, String userId) {
        //Multiple users can have the same subject, so we don't need to check if the user is the creator
        // of the subject.
        //However, we can check if the subject exists for the user.

        List<Subject> subjects = subjectRepository.findBySubjectNameAndCreatedById(subjectName, userId);
        if (subjects != null && !subjects.isEmpty()){
            return subjects.stream()
                    .map(this::entityToDto)
                    .toList();
        }
        return null;
    }

    // Helper methods to convert between User and UserDto
    private SubjectDto entityToDto(Subject saved){

        return modelMapper.map(saved,SubjectDto.class);

    }

    // Convert UserDto to User entity
    private Subject dtoToEntity(SubjectDto Dto){

        return modelMapper.map(Dto,Subject.class);
    }

    @Override
    public Subject findSubjectById(String id){
        return subjectRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Subject", "id", id));
    }


}
