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
    WIKI_REGIST_FAIL(false, 5001,"위키 등록을 실패했습니다."),
    WIKI_TITLE_REGIST_FAIL(false, 5002,"제목을 입력해주세요."),
    WIKI_CATEGORY_REGIST_FAIL(false, 5002,"카테고리를 입력해주세요."),
    WIKI_CONTENT_REGIST_FAIL(false, 5003,"내용을 입력해주세요."),
    // 에러 아카이브 기능 - 6000

    // 에러 QnA 기능 - 7000

    // 채팅 기능 - 8000

    // 카테고리 기능 - 9000
    NOT_FOUND_CATEGORY(false, 9011, "카테고리를 찾을 수 없습니다."),
    // 기타 기능 - 10000
    EMPTY_FILE(false, 10011, "빈 파일입니다."),
    INVALID_FILE_TYPE(false, 10012, "지원하지 않는 파일 형식입니다."),
    EXCEED_MAX_SIZE(false, 10013, "파일의 크기가 허용된 최대 크기를 초과하였습니다."),


    // 실패 - 40000 (위치 바꾸지 마시오)
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