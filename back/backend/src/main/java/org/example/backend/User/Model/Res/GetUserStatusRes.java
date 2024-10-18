package org.example.backend.User.Model.Res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetUserStatusRes {
    private Long userId;
    private Boolean isLoggedIn;
}
