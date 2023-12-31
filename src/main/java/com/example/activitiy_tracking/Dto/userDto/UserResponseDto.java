package com.example.activitiy_tracking.Dto.userDto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String dob;
    private String gender;
    private LocalDateTime createdAt;
}