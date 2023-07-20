package com.example.activitiy_tracking.service;

import com.example.activitiy_tracking.Dto.userDto.UserResponseDto;
import com.example.activitiy_tracking.Dto.userDto.UserSignupDto;
import com.example.activitiy_tracking.exception.AlreadyExistsException;
import com.example.activitiy_tracking.exception.NotFoundException;
import com.example.activitiy_tracking.exception.NotNullException;
import com.example.activitiy_tracking.model.ApiResponse;
import com.example.activitiy_tracking.model.User;

public interface UserService {
    boolean isUserExist(String email);

    boolean isUserExistByEmailAndPassword(String email, String Password);

    ApiResponse<User> signup(UserSignupDto userSignupDto) throws AlreadyExistsException, NotNullException;
    ApiResponse<UserResponseDto> login(String email, String password) throws NotNullException, NotFoundException;
}