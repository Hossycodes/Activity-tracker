package com.example.activitiy_tracking.Dto.userDto;

import lombok.Data;

@Data
public class UserSignupDto {
    private String firstName;
    private String lastName;
    private String email;
    private String password;
    private String dob;
    private String gender;
}
