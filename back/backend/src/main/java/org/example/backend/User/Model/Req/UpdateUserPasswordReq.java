package org.example.backend.User.Model.Req;

import lombok.Getter;

@Getter
public class UpdateUserPasswordReq {
    private String oldPassword;
    private String newPassword;
    private String confirmPassword;
}
