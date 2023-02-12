package com.example.mediaservice.dto;


import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto extends GenericDto{

    private String login;
    private String password;
    private RoleDto role;
    private String firstName;
    private String lastName;
    private String middleName;
    private String email;
    private String phone;
    private String address;
    private String  birthDate;

}
