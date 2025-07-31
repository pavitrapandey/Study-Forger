package com.studyForger.Study_Forger.UserTest;


import com.studyForger.Study_Forger.Dto.PageableRespond;
import com.studyForger.Study_Forger.User.User;
import com.studyForger.Study_Forger.User.UserDto;
import com.studyForger.Study_Forger.User.UserRepository;
import com.studyForger.Study_Forger.User.UserService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.bean.override.mockito.MockitoBean;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@SpringBootTest
public class UserServiceTest {

    @MockitoBean
    private UserRepository userRepository;

    @Autowired
    private UserService userService;

    private User user;

    @BeforeEach
    public void init(){
        user=User.builder()
                .id("12345")
                .name("Test User")
                .email("tester123@example.com")
                .username("testuser")
                .password("password123")
                .imageName("default.png")
                .about("This is a test user.")
                .subjects(List.of())
                .build();
    }

    //create user test
    @Test
    public void createUserTest(){

        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto savedUser = userService.createUser(new UserDto());

        Assertions. assertNotNull(savedUser);
    }

    //update user test
    @Test
    public void updateUserTest() {

        String userId="12345";
        UserDto info = UserDto.builder().name("John Doe")
                .email("john@doe.com")
                .username("boxUser")
                .password("herHeadIsInTheBox")
                .about("What's in the box")
                .imageName("box.jpg").build();

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any())).thenReturn(user);
        UserDto updatedUser = userService.updateUser(userId, info);

        Assertions.assertNotNull(updatedUser);

    }

    //get user by id test
    @Test
    public void getUserByIdTest() {

        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        UserDto userDto = userService.getUserById("12345");
        System.out.println("User Name is "+userDto.getName());
        Assertions.assertNotNull(userDto);

    }

    //get all users test
    @Test
    public void getAllUsersTest() {

        User user1=User.builder()
                .id("12341")
                .name("Walter White")
                .email("white123@example.com")
                .about("Say my name!")
                .username("heisenberg")
                .password("UrGoddamnRight")
                .imageName("heisenberg.png")
                .subjects(List.of())
                .build();

        User user2=User.builder()
                .id("12342")
                .name("Jesse Pinkman")
                .email("pinkman@gmail.com")
                .about("Yo! Bitch!")
                .username("pinkman")
                .password("Yeah Science")
                .imageName("Badger.png")
                .subjects(List.of())
                .build();
        List<User> userList= Arrays.asList(user,user1,user2);
        Page<User> page= new PageImpl<>(userList);

        Mockito.when(userRepository.findAll(Mockito.any(Pageable.class))).thenReturn(page);

        // 🧪 Call the method under test
        PageableRespond<UserDto> response = userService.getAllUsers(0, 10, "name", "asc");

        // ✅ Assert the size
        Assertions.assertEquals(3, response.getContent().size());

    }

    //get user by email test
    @Test
    public void getUserByEmailTest() {
        Mockito.when(userRepository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(user));
        UserDto userDto = userService.getUserByEmail("tester123@example.com");
        System.out.println("User Email is " + userDto.getEmail());
        Assertions.assertNotNull(userDto);
    }

    //delete user test
    @Test
    public void deleteUserTest() {
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        userService.deleteUser("12345");
        Mockito.verify(userRepository, Mockito.times(1)).delete(Mockito.any(User.class));
    }

}
