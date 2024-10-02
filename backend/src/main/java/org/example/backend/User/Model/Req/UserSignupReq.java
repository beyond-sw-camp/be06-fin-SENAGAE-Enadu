package org.example.backend.User.Model.Req;


import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class UserSignupReq {
    private String nickname;
    @Email
    private String email;
    private String password;
}
