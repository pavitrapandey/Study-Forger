package com.studyForge.Study_Forge.User;

import com.studyForge.Study_Forge.Files.ImageResponse;
import com.studyForge.Study_Forge.Service.FileService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController{

    @Autowired
    private UserService userService;

    @Autowired
    private FileService fileService;

    @Value("${user.profile.image.path}")
    private String imageUploadPath;


    @PostMapping
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto){
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        UserDto userDto = userService.getUserById(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> usersDto = userService.getAllUsers();
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable String userId, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(userId, userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        UserDto userDto = userService.getUserByEmail(email);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    //Upload user image
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> uploadUserImage(
            @PathVariable String userId,
            @RequestParam("image") MultipartFile image) throws IOException {

        String imageName=  fileService.uploadImage(image,imageUploadPath);
        UserDto user= userService.getUserById(userId);
        user.setImageName(imageName);
        UserDto updated= userService.updateUser(userId,user);

        ImageResponse imageResponse=  ImageResponse.builder().imageName(imageName).success(true).message("Image Uploaded").status(HttpStatus.CREATED).build();

        return new ResponseEntity<>(imageResponse, HttpStatus.CREATED);
    }

    //serve image
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDto user = userService.getUserById(userId);
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

    @DeleteMapping("/image/{userId}")
    public ResponseEntity<ImageResponse> deleteUserImage(@PathVariable String userId) {
        try {
            UserDto user = userService.getUserById(userId);
            String imageName = user.getImageName();

            if (imageName == null || imageName.trim().isEmpty()) {
                return ResponseEntity.badRequest()
                    .body(ImageResponse.builder()
                        .success(false)
                        .message("No image found for user")
                        .status(HttpStatus.BAD_REQUEST)
                        .build());
            }

            try {
                fileService.deleteImage(imageUploadPath, imageName);

                // Update user's image name to null after successful deletion
                user.setImageName(null);
                userService.updateUser(userId, user);

                return ResponseEntity.ok()
                    .body(ImageResponse.builder()
                        .success(true)
                        .message("Image deleted successfully")
                        .status(HttpStatus.OK)
                        .build());
            } catch (FileNotFoundException e) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(ImageResponse.builder()
                        .success(false)
                        .message("Image file not found")
                        .status(HttpStatus.NOT_FOUND)
                        .build());
            }
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body(ImageResponse.builder()
                    .success(false)
                    .message("User not found with ID: " + userId)
                    .status(HttpStatus.NOT_FOUND)
                    .build());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ImageResponse.builder()
                    .success(false)
                    .message("Error while deleting image: " + e.getMessage())
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .build());
        }
    }
}
