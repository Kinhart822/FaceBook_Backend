package com.spring.dto.Request.User;

import com.spring.enums.EducationLevel;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserAboutRequest {
    private String occupation;
    private String workPlace;
    private EducationLevel educationLevel;
    private String school;
    private String dateOfJoining;
    private Integer locationId;
    private String relationshipStatus;
}
