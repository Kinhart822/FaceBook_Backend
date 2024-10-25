package com.spring.dto.Response.User;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserFollowerResponse {
    private String firstName;
    private String lastName;
    private Date dateOfBirth;
    private String gender;
}
