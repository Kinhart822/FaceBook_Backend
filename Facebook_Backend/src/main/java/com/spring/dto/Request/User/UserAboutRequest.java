package com.spring.dto.Request.User;

import com.spring.enums.EducationLevel;
import com.spring.enums.Gender;
import com.spring.enums.Relationship;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class UserAboutRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date dateOfBirth;
    private Gender gender;
    private String userName;
    private String occupation;
    private String workPlace;
    private EducationLevel educationLevel;
    private String school;
    private Date dateOfJoining;
    private Relationship relationshipStatus;
    private Boolean location;
    private String city;
    private String country;
    private String state;
    private String street;
    private String region;
    private String imageUrl;
}
