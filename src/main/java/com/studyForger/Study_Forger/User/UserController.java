package com.studyForger.Study_Forger.User;

import com.studyForger.Study_Forger.Dto.PageableRespond;
import com.studyForger.Study_Forger.Files.ImageResponse;
import com.studyForger.Study_Forger.Files.FileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
import com.studyForger.Study_Forger.Configuration.AppConstants;


@CrossOrigin(AppConstants.FRONT_END_URL)
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
    @Operation(method = "POST",description = "Create a new user")
    @ApiResponses(value={
            @ApiResponse(description = "User created successfully", responseCode = "201"),
            @ApiResponse(description = "User already exists", responseCode = "400"),
            @ApiResponse(description = "Invalid request", responseCode = "400")
    })
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto){
        UserDto createdUser = userService.createUser(userDto);
        return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    @Operation(method = "GET",description = "Get user by ID")
    @ApiResponses(value={
            @ApiResponse(description = "User found", responseCode = "200"),
            @ApiResponse(description = "User not found", responseCode = "404")
    })
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId) {
        UserDto userDto = userService.getUserById(userId);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    @GetMapping
    @Operation(method = "GET",description = "Get all users")
    @ApiResponses(value={
            @ApiResponse(description = "Users found", responseCode = "200"),
            @ApiResponse(description = "No users found", responseCode = "404")
    })
    public ResponseEntity<PageableRespond<UserDto>> getAllUsers(
            @RequestParam(value = "pageNumber", defaultValue = "0", required = false) int pageNumber,
            @RequestParam(value = "pageSize", defaultValue = "10", required = false) int pageSize,
            @RequestParam(value = "sortBy", defaultValue = "name", required = false) String sortBy,
            @RequestParam(value = "sortDir", defaultValue = "asc", required = false) String sortDir
    ) {
        PageableRespond<UserDto> usersDto = userService.getAllUsers(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(usersDto, HttpStatus.OK);
    }

    @PutMapping("/{userId}")
    @Operation(method = "PUT",description = "Update user details")
    @ApiResponses(value={
            @ApiResponse(description = "User updated successfully", responseCode = "200"),
            @ApiResponse(description = "User not found", responseCode = "404")
    })
    public ResponseEntity<UserDto> updateUser(@Valid @PathVariable String userId, @RequestBody UserDto userDto) {
        UserDto updatedUser = userService.updateUser(userId, userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    @Operation(method = "DELETE",description = "Delete user by ID")
    @ApiResponses(value={
            @ApiResponse(description = "User deleted successfully", responseCode = "204"),
            @ApiResponse(description = "User not found", responseCode = "404")
    })
    public ResponseEntity<Void> deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);

        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/email/{email}")
    @Operation(method = "GET",description = "Get user by email")
    @ApiResponses(value={
            @ApiResponse(description = "User found", responseCode = "200"),
            @ApiResponse(description = "User not found", responseCode = "404")
    })

    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email) {
        UserDto userDto = userService.getUserByEmail(email);
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    //Upload user image
    @PostMapping("/image/{userId}")
    @Operation(method = "POST",description = "Upload user image")
    @ApiResponses(value={
            @ApiResponse(description = "Image uploaded successfully", responseCode = "201"),
            @ApiResponse(description = "User not found", responseCode = "404"),
            @ApiResponse(description = "Invalid request", responseCode = "400")
    })

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
    @Operation(method = "GET",description = "Serve user image")
    @ApiResponses(value={
            @ApiResponse(description = "Image served successfully", responseCode = "200"),
            @ApiResponse(description = "User not found", responseCode = "404"),
            @ApiResponse(description = "Image not found", responseCode = "404")
    })

    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDto user = userService.getUserById(userId);
        InputStream resource = fileService.getResource(imageUploadPath, user.getImageName());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resource, response.getOutputStream());
    }

    @DeleteMapping("/image/{userId}")
    @Operation(method = "DELETE",description = "Delete user image")
    @ApiResponses(value={
            @ApiResponse(description = "Image deleted successfully", responseCode = "204"),
            @ApiResponse(description = "User not found", responseCode = "404"),
            @ApiResponse(description = "Image not found", responseCode = "404")
    })
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
