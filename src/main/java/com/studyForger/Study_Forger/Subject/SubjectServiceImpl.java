package com.studyForger.Study_Forger.Subject;

import com.studyForger.Study_Forger.Dto.PageableRespond;
import com.studyForger.Study_Forger.Exception.BadApiRequest;
import com.studyForger.Study_Forger.Exception.NotFoundException;
import com.studyForger.Study_Forger.Exception.ResourceNotFoundException;
import com.studyForger.Study_Forger.Helper.helper;
import com.studyForger.Study_Forger.User.User;
import com.studyForger.Study_Forger.User.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
    public PageableRespond<SubjectDto> getAllSubjectsByUserId(String userId, int pageNumber, int pageSize,String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending()) ;
        Pageable pageable= PageRequest.of(pageNumber, pageSize, sort);



        User user = userService.findUserById(userId);
        if(user == null){
            throw new NotFoundException("User not found with id: " + userId);
        }
        Page<Subject> subjects = subjectRepository.findByCreatedById(userId,pageable);
         if (subjects != null && !subjects.isEmpty()) {
            List<SubjectDto> dtos= subjects.stream()
                    .map(this::entityToDto)
                    .toList();
        }
         //return empty list
       if(subjects==null||subjects.isEmpty()){
           throw new NotFoundException("Subject not found for user: " + userId);
         }
        return helper.getPageableResponse(subjects, SubjectDto.class);
    }

    @Override
    public void deleteSubject(String subjectId){
        Subject subject = subjectRepository.findById(subjectId).orElseThrow(() -> new BadApiRequest("Subject not found with id: " + subjectId));
        subjectRepository.delete(subject);

    }

    @Override
    public PageableRespond<SubjectDto> searchSubjectByName(String subjectName, String userId,int pageNumber, int pageSize,String sortBy, String sortDir) {

        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending()) ;
        Pageable pageable= PageRequest.of(pageNumber, pageSize, sort);
        User user = userService.findUserById(userId);
        if(user == null){
            throw new NotFoundException("User not found with id: " + userId);
        }

        Page<Subject> subjects = subjectRepository.findBySubjectNameAndCreatedById(subjectName, userId,pageable);
        if (subjects == null || subjects.isEmpty()) {
            throw new NotFoundException("No subjects found with name: " + subjectName + " for user: " + userId);
        }

            List<SubjectDto> dtos= subjects.stream()
                    .map(this::entityToDto)
                    .toList();

        return helper.getPageableResponse(subjects, SubjectDto.class);
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

    @Override
    public String getUserIdBySubjectId(String subjectId) {
        Subject subject = subjectRepository.findById(subjectId)
                .orElseThrow(() -> new NotFoundException("Subject not found with id: " + subjectId));
        return subject.getCreatedBy().getId();
    }
}
