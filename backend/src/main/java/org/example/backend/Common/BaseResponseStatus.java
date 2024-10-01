package org.example.backend.Common;

import lombok.Getter;

@Getter
public enum BaseResponseStatus {
    // 모든 요청 성공 1000
    SUCCESS(true, 1000, "요청이 성공하였습니다."),

    // 회원 기능 - 2000
    USER_FAIL(false, 2001, "요청이 실패하였습니다."),
    USER_INVALID_REQUEST_BODY(false, 2002, "잘못된 요청 본문입니다."),
    USER_JSON_PARSE_ERROR(false, 2003, "JSON 파싱 오류가 발생했습니다."),
    USER_EMAIL_OR_PASSWORD_NULL(false, 2004, "이메일 또는 비밀번호가 비어있습니다."),
    USER_NOT_FOUND(false, 2005, "등록되지 않은 사용자입니다."),
    USER_INVALID_EMAIL_FORMAT(false, 2005, "잘못된 이메일 형식입니다."),
    USER_INACTIVE_ACCOUNT(false, 2006, "탈퇴한 계정입니다."),
    USER_EMAIL_NOT_VERIFIED(false, 2007, "이메일 인증이 완료되지 않았습니다."),
    USER_INVALID_CREDENTIALS(false, 2008, "잘못된 자격 증명입니다."),
    USER_NOT_LOGIN(false, 2011, "로그인 하지 않은 사용자입니다."),
    USER_EMAIL_NOT_FOUND_IN_GITHUB(false, 2021, "GitHub 이메일 정보를 가져올 수 없습니다."),
    USER_INVALID_TYPE(false,2022,"소셜로 가입한 유저가 아닙니다."),
    USER_INVALID_NICKNAME(false, 2023,"닉네임이 비어있습니다."),
    USER_DUPLICATE_NICKNAME(false, 2031, "이미 있는 닉네임입니다."),
    USER_DUPLICATE_EMAIL(false,2032,"이미 있는 이메일입니다"),
    USER_PASSWORDS_DO_NOT_MATCH(false,2041, "비밀번호가 일치하지 않습니다."),
    USER_NEW_PASSWORDS_DO_NOT_MATCH(false, 2042, "새로운 비밀번호와 비밀번호 확인이 일치하지 않습니다"),
    USER_ACCESS_TOKEN_NOT_FOUND(false, 2051, "소셜 토큰을 찾을 수 없습니다."),


    // 이메일 인증 실패
    USER_EMAIL_VERIFY_FAIL(false,2500,"이메일 인증에 실패했습니다"),


    // 마이페이지 기능 - 3000
    MYPAGE_NO_USER_ID(false, 3001, "유저 정보가 없습니다."),

    // 포인트 기능 - 4000

    // 위키 기능 - 5000
    WIKI_REGIST_FAIL(false, 5001,"위키 등록을 실패했습니다."),
    WIKI_TITLE_REGIST_FAIL(false, 5002,"제목을 입력해주세요."),
    WIKI_CONTENT_REGIST_FAIL(false, 5003,"내용을 입력해주세요."),
    WIKI_CONTENT_DUPLICATION_FAIL(false, 5004,"중복되는 위키입니다."),
    WIKI_NOT_FOUND_DETAIL(false,5005,"위키 조회를 실패했습니다."),
    WIKI_PERMISSION_DENIED(false, 5006,"수정 권한이 없습니다."),
    WIKI_SEARCH_EMPTY_REQUEST(false, 5007, "검색 요청이 비어 있습니다."),
    WIKI_INVALID_SEARCH_TYPE(false, 5008, "존재하지 않은 타입의 검색 방식 입니다."),

    // 에러 아카이브 기능 - 6000

    ERRORARCHIVE_INVALID_SEARCH_TYPE(false, 6060, "존재하지 않은 타입의 검색 방식 입니다."),
    ERRORARCHIVE_NOT_FOUND(false,6022, "삭제된 게시글입니다."),
    ERRORARCHIVE_SEARCH_EMPTY_REQUEST(false, 6061, "검색 요청이 비어 있습니다."),
    ERRORARCHIVE_NOT_FOUND_DETAIL(false,6023,"해당 게시글이 존재하지 않습니다"),
    ERRORARCHIVE_BEFORE_LIKE(false,6024,"이전에 좋아요를 눌렀던 게시글입니다"),
    ERRORARCHIVE_BEFORE_Hate(false,6025,"이전에 싫어요를 눌렀던 게시글입니다"),
    ERRORARCHIVE_BEFORE_SCRAP(false,6026,"이전에 스크랩 했던 게시글입니다"),
    ERROR_ALREADY_LIKED(false,6027, "이미 좋아요를 눌렀던 게시글입니다."),
    ERROR_ALREADY_HATED(false, 6028, "이미 싫어요를 눌렀던 게시글입니다"),
    ERROR_PERMISSION_DENIED(false,6029,"권한이 없습니다"),


    // 에러 QnA 기능 - 7000

    // - QnA 공통 에러
    QNA_FAIL(false, 7001, "요청이 실패하였습니다."),
    QNA_INVALID_OR_EMPTY_DATA(false, 7002, "데이터 값이 비어있거나, 유효하지 않은 데이터입니다."),
    QNA_NO_EDIT_PERMISSION(false, 7003, "수정 및 삭제 권한이 없습니다."),
    QNA_INVALID_SEARCH_TYPE(false, 7004, "존재하지 않은 타입의 검색 방식 입니다."),
    QNA_CONFLICT_LIKE_DISLIKE(false, 7010, "좋아요와 싫어요는 동시에 표기할 수 없습니다."),
    // - QnA 질문 에러
    QNA_QUESTION_NOT_FOUND(false, 7101, "해당 질문이 존재하지 않습니다."),
    QNA_INVALID_QUESTION_FORMAT(false, 7102, "잘못된 형식의 질문입니다."),
    QNA_ALREADY_ADOPTED(false, 7103, "해당 질문은 이미 채택된 답변이 존재합니다."),
    QNA_ANSWERED_EDIT(false, 7104, "답변이 작성된 질문글은 수정할 수 없습니다."),
    // - QnA 답변 에러
    QNA_ANSWER_NOT_FOUND(false, 7201, "해당 답변이 존재하지 않습니다."),
    QNA_INVALID_ANSWER_FORMAT(false, 7202, "잘못된 형식의 답변입니다."),
    QNA_ADOPTED_EDIT(false, 7203, "이미 채택된 경우 답변을 수정할 수 없습니다."),




    // 채팅 기능 - 8000
    CHAT_INVALID_CHATROOM_ID(false, 8011, "채팅방을 조회할 수 없습니다."),
    CHAT_INVALID_USER_ID(false, 8012, "채팅방에 참여하지 않는 사용자 입니다."),

    // 카테고리 기능 - 9000
    CATEGORY_FAIL(false, 9001, "요청이 실패하였습니다."),
    CATEGORY_INVALID_CATEGORY_DATA(false,  9002, "유효하지 않은 카테고리 데이터입니다."),
    CATEGORY_DUPLICATE_CATEGORY(false, 9003, "이미 존재하는 카테고리입니다."),
    CATEGORY_NOT_FOUND_CATEGORY(false, 9011, "카테고리를 찾을 수 없습니다."),
    CATEGORY_DUPLICATE_NAME(false, 9021, "이미 존재하는 카테고리입니다."),
    CATEGORY_NOT_FOUND_SUPER_CATEGORY(false, 9022, "상위 카테고리를 찾을 수 없습니다."),

  // 기타 기능 - 10000
    EMPTY_FILE(false, 10011, "빈 파일입니다."),
    INVALID_FILE_TYPE(false, 10012, "지원하지 않는 파일 형식입니다."),
    EXCEED_MAX_SIZE(false, 10013, "파일의 크기가 허용된 최대 크기를 초과하였습니다."),
    INTERNAL_SERVER_ERROR(false,10014,"에러가 발생했습니다"),

    // 이메일 인증 - 11000
    EMAIL_VERIFY_FAIL(false, 11001, "이메일 인증에 실패하였습니다"),
    UUID_ALREADY_USED(false,11002,"이미 사용된 UUID 입니다"),


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