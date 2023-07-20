package com.example.activitiy_tracking.controller;

import com.example.activitiy_tracking.Dto.userDto.UserResponseDto;
import com.example.activitiy_tracking.Dto.userDto.UserSignupDto;
import com.example.activitiy_tracking.exception.AlreadyExistsException;
import com.example.activitiy_tracking.exception.NotFoundException;
import com.example.activitiy_tracking.exception.NotNullException;
import com.example.activitiy_tracking.model.ApiResponse;
import com.example.activitiy_tracking.model.User;
import com.example.activitiy_tracking.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/activity-tracker")
public class UserController {
    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<User>> signup(@RequestBody UserSignupDto userSignupDto) throws AlreadyExistsException, NotNullException {
        ApiResponse<User> user = userService.signup(userSignupDto);
        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }

    @GetMapping("/login")
    public ResponseEntity<ApiResponse<UserResponseDto>> login(@RequestBody User user) throws NotFoundException, NotNullException {
        ApiResponse<UserResponseDto> userResponseDtoApiResponse = userService.login(user.getEmail(), user.getPassword());
        return new ResponseEntity<>(userResponseDtoApiResponse, HttpStatus.ACCEPTED);
    }


}
