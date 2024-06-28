package com.pradeep.demo_jwt_authentication.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class LoginDto {
    private String usernameOrEmail;
    private String password;
}
