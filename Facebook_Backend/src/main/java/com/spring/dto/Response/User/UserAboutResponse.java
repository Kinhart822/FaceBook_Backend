package com.spring.dto.Response.User;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserAboutResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date dateOfBirth;
    private String userName;
    private String gender;
    private String occupation;
    private String workPlace;
    private String educationLevel;
    private String school;
    private Date dateOfJoining;
    private String locationName;
    private String relationshipStatus;
    private String profilePhotoUrl;
    private String avatarPhotoUrl;
    private String backgroundUrl;
}
