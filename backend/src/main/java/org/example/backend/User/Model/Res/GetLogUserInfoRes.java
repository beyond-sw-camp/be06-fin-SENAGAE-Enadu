package org.example.backend.User.Model.Res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetLogUserInfoRes {
    private final Long id;
    private final String nickname;
    private final String profileImg;
    private final String grade;
}
