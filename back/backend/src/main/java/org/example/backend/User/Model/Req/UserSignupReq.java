package org.example.backend.User.Model.Req;


import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UserSignupReq {
    private String nickname;
    @Email
    private String email;
    private String password;
    private String confirmPassword;
}
