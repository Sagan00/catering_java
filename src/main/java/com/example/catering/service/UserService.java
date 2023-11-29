package com.example.catering.service;
import com.example.catering.dto.UserDto;
import com.example.catering.entity.User;

import java.util.List;

public interface UserService {
    void saveUser(UserDto userDto);
    void deleteUserByEmail(String email);

    User findUserByEmail(String email);

    List<UserDto> findAllUsers();
}