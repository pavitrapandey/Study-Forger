package com.studyForger.Study_Forger.User;

import com.studyForger.Study_Forger.Dto.PageableRespond;
import com.studyForger.Study_Forger.Exception.EmailAlreadyExistException;
import com.studyForger.Study_Forger.Exception.NotFoundException;
import com.studyForger.Study_Forger.Exception.ResourceNotFoundException;
import com.studyForger.Study_Forger.Exception.UsernameAlreadyTakenException;
import com.studyForger.Study_Forger.Helper.helper;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ModelMapper modelMapper;


    @Value("${user.profile.image.path}")
    private String filePath;


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
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

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
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));
        // Convert User Entity to UserDto
        return entityToUserDto(user);
    }

    @Override
    public PageableRespond<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir){
        // Create a sort object based on the provided sort direction
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()) :(Sort.by(sortBy).ascending()) ;
        Pageable pageable= PageRequest.of(pageNumber, pageSize, sort);

        // Create a pageable object with the specified page number and size
        Page<User> page = userRepository.findAll(pageable);

        // Fetch all users from the repository
        List<User> users= page.getContent();
            // Convert List<User> to List<UserDto>
            List<UserDto> dto= users.stream()
                    .map(this::entityToUserDto)
                    .toList();

        // Return an empty list if no users are found
        return helper.getPageableResponse(page,UserDto.class);
    }

    @Override
    public void deleteUser(String userId){
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundException("User not found with id: " + userId));

        String imageName=user.getImageName();
        String fullPath=filePath+imageName;

        Path path= Path.of(fullPath);
        try {
            Files.delete(path);
        } catch (Exception e) {
            throw new NotFoundException("Error deleting image file: " + e.getMessage());
        }

        userRepository.delete(user);

    }

    @Override
    public UserDto getUserByEmail(String email){
        Optional<User> userOptional = userRepository.findByEmail(email);
    if(userOptional.isEmpty()){
        throw new NotFoundException("User not found with email: " + email);
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

    @Override
    public User findUserById(String id) {
        return userRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User", "id", id));
    }

}
