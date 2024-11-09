package com.spring.dto.response.User;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SearchUserByNameResponse {
    private String firstName;
    private String lastName;
    private String email;
    private String phone;
    private Date dateOfBirth;
}
