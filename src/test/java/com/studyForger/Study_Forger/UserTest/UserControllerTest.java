package com.studyForger.Study_Forger.UserTest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.studyForger.Study_Forger.Dto.PageableRespond;
import com.studyForger.Study_Forger.Exception.BadApiRequest;
import com.studyForger.Study_Forger.User.User;
import com.studyForger.Study_Forger.User.UserDto;
import com.studyForger.Study_Forger.User.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {

    @MockitoBean
    private UserService userService;
    private User user;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private MockMvc mvc;

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


    @Test
    public void createUserTest() throws Exception {
        // Test logic for creating a user

        UserDto dto = modelMapper.map(user, UserDto.class);
        Mockito.when(userService.createUser(Mockito.any())).thenReturn(dto);

        //Actual request fo URL
        this.mvc.perform(
                        MockMvcRequestBuilders.post("/api/users")
                                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                                .content(convertObjectToJson(dto))
                                .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect( jsonPath("$.name").exists());
    }

    // Test for updating a user
    @Test
    public void updateUserTest() throws Exception {
        String userId = "12345";
        UserDto dto = modelMapper.map(user, UserDto.class);
        Mockito.when(userService.updateUser(Mockito.anyString(), Mockito.any())).thenReturn(dto);

        this.mvc.perform(
                        MockMvcRequestBuilders.put("/api/users/" + userId)
                                .contentType(String.valueOf(MediaType.APPLICATION_JSON))
                                .content(convertObjectToJson(dto))
                                .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

    // Test for getting a user by ID
    @Test
    public void getUserByIdTest() throws Exception {
        String userId = "12345";
        UserDto dto = modelMapper.map(user, UserDto.class);
        Mockito.when(userService.getUserById(Mockito.anyString())).thenReturn(dto);

        this.mvc.perform(
                        MockMvcRequestBuilders.get("/api/users/" + userId)
                                .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").exists());
    }

    // Test for getting all users
    @Test
    public void getAllUsersTest() throws Exception {
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

        UserDto dto1 = modelMapper.map(user1, UserDto.class);
        UserDto dto2 = modelMapper.map(user2, UserDto.class);


        PageableRespond<UserDto> pageableResponse = new PageableRespond<>();
        pageableResponse.setContent(List.of(dto1, dto2));
        pageableResponse.setPageNumber(0);
        pageableResponse.setPageSize(10);


        Mockito.when(userService.getAllUsers(0, 10, "name", "asc"))
                .thenReturn(pageableResponse);


        this.mvc.perform(MockMvcRequestBuilders.get("/api/users")
                        .param("pageNumber", "0")
                        .param("pageSize", "10")
                        .param("sortBy", "name")
                        .param("sortDir", "asc")
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].name").value("Walter White"))
                .andExpect(jsonPath("$.content[1].username").value("pinkman"));

    }

    // Test for deleting a user
    @Test
    public void deleteUserTest() throws Exception {
        String userId = "12345";
        Mockito.doNothing().when(userService).deleteUser(Mockito.anyString());
        this.mvc.perform(
                        MockMvcRequestBuilders.delete("/api/users/" + userId)
                                .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andDo(print())
                .andExpect(status().isNoContent());
    }

    // Test for getting a user by email
    @Test
    public void getUserByEmailTest() throws Exception {
        String email = "tester123@example.com";
        UserDto dto = modelMapper.map(user, UserDto.class);
        Mockito.when(userService.getUserByEmail(Mockito.anyString())).thenReturn(dto);
        this.mvc.perform(
                        MockMvcRequestBuilders.get("/api/users/email/" + email)
                                .accept(String.valueOf(MediaType.APPLICATION_JSON)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value(email));
    }



    private String convertObjectToJson(Object user){
        try {
            return new ObjectMapper().writeValueAsString(user);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
            throw new BadApiRequest("Failed to convert object to JSON"+ e.getMessage());
        }
    }

    private UserDto mapToDto(User user) {
        return UserDto.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .about(user.getAbout())
                .username(user.getUsername())
                .imageName(user.getImageName())
                .build();
    }
}
