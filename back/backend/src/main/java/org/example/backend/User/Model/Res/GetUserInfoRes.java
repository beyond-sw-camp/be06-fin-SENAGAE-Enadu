package org.example.backend.User.Model.Res;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
public class GetUserInfoRes {
    private final String email;
    private final String nickname;
    private final Boolean isSocialUser;
    private final String profileImg;
    private final String grade;
}
