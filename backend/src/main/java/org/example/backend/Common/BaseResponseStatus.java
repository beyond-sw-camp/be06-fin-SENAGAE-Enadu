package org.example.backend.Common;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    // 모든 요청 성공 1000
    SUCCESS(true, 1000, "요청이 성공하였습니다."),

    // 회원 기능 - 2000

    // 마이페이지 기능 - 3000

    // 포인트 기능 - 4000

    // 위키 기능 - 5000

    // 에러 아카이브 기능 - 6000

    // 에러 QnA 기능 - 7000

    // 채팅 기능 - 8000

    // 카테고리 기능 - 9000

    // 기타 기능 - 10000

    // 실패 - 40000
    FAIL(false, 40000, "요청에 실패하였습니다.");

    private final boolean isSuccess;
    private final Integer code;
    private final String message;

    BaseResponseStatus(Boolean isSuccess, Integer code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}