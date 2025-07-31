package com.studyForger.Study_Forger.SubjectTest;

import com.studyForger.Study_Forger.Subject.Subject;
import com.studyForger.Study_Forger.Subject.SubjectDto;
import com.studyForger.Study_Forger.Subject.SubjectRepository;
import com.studyForger.Study_Forger.Subject.SubjectService;
import com.studyForger.Study_Forger.User.User;
import com.studyForger.Study_Forger.User.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.List;
import java.util.Optional;

@SpringBootTest
public class SubjectServiceTest {

    @MockitoBean
    private SubjectRepository subjectRepository;

    @Autowired
    private SubjectService subjectService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper mapper;

    private Subject subject;
    private User user;

    @BeforeEach
    public void init(){
        user=User.builder()
                .id("12345")
                .name("Test User")
                .email("tester1@example.com")
                .username("test")
                .password("password123")
                .imageName("default.png")
                .about("This is a test user.")
                .subjects(List.of())
                .build();
        userRepository.save(user);


        subject=Subject.builder()
                .id("12345")
                .subjectName("Test Subject")
                .description("This is a test subject")
                .createdBy(user)
                .build();
    }

    //Create subject
    @Test
    public void createSubjectTest(){
        String userId="12345";

        Mockito.when(subjectRepository.save(Mockito.any())).thenReturn(subject);
        SubjectDto savedSubject = subjectService.createSubject(new SubjectDto(),user.getId());
        Assertions.assertNotNull(savedSubject);
    }

    //Update subject
    @Test
    public void updateSubjectTest() {
        String subjectId="12345";
        SubjectDto info = SubjectDto.builder().subjectName("Updated Subject")
                .description("This is an updated subject")
                .build();
        Mockito.when(subjectRepository.findById(Mockito.anyString())).thenReturn(Optional.of(subject));
        Mockito.when(subjectRepository.save(Mockito.any())).thenReturn(subject);
        SubjectDto updatedSubject = subjectService.updateSubject(subjectId, info);
        Assertions.assertNotNull(updatedSubject);
    }

    //Get subject by id
    @Test
    public void getSubjectByIdTest() {
        String subjectId="12345";

        Mockito.when(subjectRepository.findById(Mockito.anyString())).thenReturn(Optional.of(subject));
        SubjectDto subjectDto = subjectService.getSubjectById(subjectId);


        System.out.println("Subject Name is "+subjectDto.getSubjectName());
        Assertions.assertNotNull(subjectDto);
    }



}
