package org.example.backend.Common;

public enum GlobalMessage {
    // 이메일 인증 관련 메시지
    EMAIL_TITLE("[SENAGAE] 인증 메세지 "),
    EMAIL_URL_TEMPLATE("http://localhost:8080/email/verify?email=%s&uuid=%s");

    private final String message;
    GlobalMessage(String message){
        this.message = message;
    }
    public String getMessage(){
        return message;
    }
    public String formatUrl(String email, String uuid){
        return String.format(message, email, uuid);
    }

}
