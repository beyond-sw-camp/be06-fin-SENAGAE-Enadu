package org.example.backend.User.Model.Req;


import jakarta.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.hibernate.annotations.processing.Pattern;

@Getter
@Builder
@AllArgsConstructor
public class UserSignupReq {
    private String nickname;
    @Email
    private String email;
    private String password;


}
