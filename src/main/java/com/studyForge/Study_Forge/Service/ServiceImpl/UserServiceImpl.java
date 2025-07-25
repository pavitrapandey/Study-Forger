package com.studyForge.Study_Forge.Service.ServiceImpl;

import com.studyForge.Study_Forge.Dto.UserDto;
import com.studyForge.Study_Forge.Entity.User;
import com.studyForge.Study_Forge.Exception.EmailAlreadyExistException;
import com.studyForge.Study_Forge.Exception.UsernameAlreadyTakenException;
import com.studyForge.Study_Forge.Repository.UserRepository;
import com.studyForge.Study_Forge.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(UserDto userDto)
    {
        String userId = UUID.randomUUID().toString();
        userDto.setId(userId);
        Optional<User> existingEmail=userRepository.findByEmail(userDto.getEmail());
        if(existingEmail.isPresent())
        {
            throw new EmailAlreadyExistException("Email"+ userDto.getEmail() + " already exists");
        }
        // Check if the username already exists
        User existingUsername = userRepository.findByUsername(userDto.getUsername());
        if (existingUsername != null) {
            throw new UsernameAlreadyTakenException("Username " + userDto.getUsername() + " already exists.");
        }
        //Convert UserDto to User Entity
        User user = dtoToEntity(userDto);
        user.setId(user.getId());
        user.setEmail(user.getEmail());
        user.setName(user.getName());
        user.setPassword(user.getPassword());
        user.setImageName(user.getImageName());
        user.setAbout(user.getAbout());
        user.setUsername(userDto.getUsername());
        userRepository.save(user);
        //Convert User Entity to UserDto
        return entityToUserDto(user);
    }

    @Override
    public UserDto updateUser(String userId, UserDto userDto){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));

        // Check if the email already exists for another user
        Optional<User> existingUser = userRepository.findByEmail(userDto.getEmail());

                 if (existingUser.isPresent() && !existingUser.get().getId().equals(userId)) {
            throw new EmailAlreadyExistException("Email " + userDto.getEmail() + " already exists for another user.");
        }
         //check if username exist or already taken
        User existingUsername = userRepository.findByUsername(userDto.getUsername());
        if (existingUsername != null && !existingUsername.getId().equals(userId)) {
            throw new UsernameAlreadyTakenException("Username " + userDto.getUsername() + " already exists for another user.");
        }
         // Update user fields
        user.setUsername(userDto.getUsername());
        user.setEmail(userDto.getEmail());
        user.setName(userDto.getName());
        user.setPassword(userDto.getPassword());
        user.setAbout(userDto.getAbout());
        user.setImageName(userDto.getImageName());
        // Save the updated user
        User savedUser = userRepository.save(user);
        // Convert User Entity to UserDto
        return entityToUserDto(savedUser);
    }

    @Override
    public UserDto getUserById(String userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        // Convert User Entity to UserDto
        return entityToUserDto(user);
    }

    @Override
    public List<UserDto> getAllUsers(){
        List<User> users = userRepository.findAll();
        if (!users.isEmpty()) {
            // Convert List<User> to List<UserDto>
            return users.stream()
                    .map(this::entityToUserDto)
                    .toList();
        }
        // Return an empty list if no users are found
        return List.of();
    }

    @Override
    public void deleteUser(String userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        userRepository.delete(user);

    }

    @Override
    public UserDto getUserByEmail(String email){
        Optional<User> userOptional = userRepository.findByEmail(email);
    if(userOptional.isEmpty()){
        throw new RuntimeException("User not found with email: " + email);
    }
        User user = userOptional.get();
        // Convert User Entity to UserDto
        return entityToUserDto(user);
    }

    // Helper methods to convert between User and UserDto
    private UserDto entityToUserDto(User savedUser){

        return modelMapper.map(savedUser,UserDto.class);

    }

    // Convert UserDto to User entity
    private User dtoToEntity(UserDto userDto){

        return modelMapper.map(userDto,User.class);
    }

}
