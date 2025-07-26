package com.studyForge.Study_Forge.User;

import java.util.List;

public interface UserService {
    //create User
    UserDto createUser(UserDto userDto);

    //update user
    UserDto updateUser(String userId, UserDto userDto);

    //get user by id
    UserDto getUserById(String userId);

    //get user all Users
    List<UserDto> getAllUsers();

    //delete user by id
    void deleteUser(String userId);

    //get user by email
    UserDto getUserByEmail(String email);

    User findUserById(String userId);
}
