package org.example.backend.Common;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    // 모든 요청 성공 1000
    SUCCESS(true, 1000, "요청이 성공하였습니다."),

    // 회원 기능 - 2000
    UNREGISTERED_USER(false, 9999, "등록되지 않은 사용자입니다."),

    USER_FAIL(false, 2001, "요청이 실패하였습니다."),
    USER_INVALID_REQUEST_BODY(false, 2002, "잘못된 요청 본문입니다."),
    USER_JSON_PARSE_ERROR(false, 2003, "JSON 파싱 오류가 발생했습니다."),
    USER_EMAIL_OR_PASSWORD_NULL(false, 2004, "이메일 또는 비밀번호가 비어있습니다."),
    USER_NOT_FOUND(false, 2005, "등록되지 않은 사용자입니다."),
    USER_INVALID_EMAIL_FORMAT(false, 2005, "잘못된 이메일 형식입니다."),
    USER_INACTIVE_ACCOUNT(false, 2006, "비활성화된 계정입니다."),
    USER_EMAIL_NOT_VERIFIED(false, 2007, "이메일 인증이 완료되지 않았습니다."),
    USER_INVALID_CREDENTIALS(false, 2008, "잘못된 자격 증명입니다."),

    // 마이페이지 기능 - 3000

    // 포인트 기능 - 4000

    // 위키 기능 - 5000
    WIKI_REGIST_FAIL(false, 5001,"위키 등록을 실패했습니다."),
    WIKI_TITLE_REGIST_FAIL(false, 5002,"제목을 입력해주세요."),
    WIKI_CATEGORY_REGIST_FAIL(false, 5002,"카테고리를 입력해주세요."),
    WIKI_CONTENT_REGIST_FAIL(false, 5003,"내용을 입력해주세요."),
    // 에러 아카이브 기능 - 6000

    // 에러 QnA 기능 - 7000
    // - QnA 공통 에러
    INVALID_OR_EMPTY_DATA(false, 7001, "데이터 값이 비어있거나, 유효하지 않은 데이터입니다."),
    NO_EDIT_PERMISSION(false, 7002, "수정 및 삭제 권한이 없습니다."),
    INVALID_SEARCH_TYPE(false, 7003, "존재하지 않은 타입의 검색 방식 입니다."),
    CONFLICT_LIKE_DISLIKE(false, 7010, "좋아요와 싫어요는 동시에 표기할 수 없습니다."),
    // - QnA 질문 에러
    QUESTION_NOT_FOUND(false, 7101, "해당 질문이 존재하지 않습니다."),
    INVALID_QUESTION_FORMAT(false, 7102, "잘못된 형식의 질문입니다."),
    // - QnA 답변 에러
    NOT_FOUND_ANSWER(false, 7201, "해당 답변이 존재하지 않습니다."),
    INVALID_ANSWER_FORMAT(false, 7202, "잘못된 형식의 답변입니다."),




    // 채팅 기능 - 8000
    CHAT_INVALID_CHATROOM_ID(false, 8011, "채팅방을 조회할 수 없습니다."),

    // 카테고리 기능 - 9000
    DUPLICATE_CATEGORY(false, 9999, "이미 존재하는 카테고리입니다."),
    INVALID_CATEGORY_DATA(false,  9999, "유효하지 않은 카테고리 데이터입니다."),
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