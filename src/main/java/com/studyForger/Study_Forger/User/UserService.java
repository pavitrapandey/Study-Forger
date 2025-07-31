package com.studyForger.Study_Forger.User;

import com.studyForger.Study_Forger.Dto.PageableRespond;

public interface UserService {
    //create User
    UserDto createUser(UserDto userDto);

    //update user
    UserDto updateUser(String userId, UserDto userDto);

    //get user by id
    UserDto getUserById(String userId);

    //get user all Users
    PageableRespond<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    //delete user by id
    void deleteUser(String userId);

    //get user by email
    UserDto getUserByEmail(String email);

    User findUserById(String userId);
}
