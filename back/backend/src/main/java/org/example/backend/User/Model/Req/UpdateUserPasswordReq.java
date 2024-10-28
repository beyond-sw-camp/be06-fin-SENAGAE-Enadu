package org.example.backend.User.Model.Req;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class UpdateUserPasswordReq {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
