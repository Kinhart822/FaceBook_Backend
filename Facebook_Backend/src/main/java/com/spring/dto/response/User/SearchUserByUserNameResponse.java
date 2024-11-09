package com.spring.dto.response.User;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchUserByUserNameResponse {
    private String firstName;
    private String lastName;
    private String userName;
    private String email;
    private String phone;
    private Date dateOfBirth;
    private String imageUrl;
}
