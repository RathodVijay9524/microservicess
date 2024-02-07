package com.vijay.UserService.service;

import com.vijay.UserService.model.PageableResponse;
import com.vijay.UserService.model.UserDto;
import com.vijay.UserService.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public interface UserServce {


    UserDto createUser(UserDto userDto);
    PageableResponse<UserDto> getAllUsers(int pageNumber, int pageSize, String sortBy, String sortDir);

    UserDto findUserById(String userId);

    UserDto updateUser(String userId, UserDto userDto);

    void deleteUserById(String userId);

    UserDto getUserByEmail(String email);

    List<UserDto> searchUser(String keywords);
}
