package org.example.backend.Common;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;

import static org.example.backend.Common.BaseResponseStatus.SUCCESS;

@Getter
@AllArgsConstructor
@JsonPropertyOrder({"code", "isSuccess", "message", "result"})
public class BaseResponse<T> {
    @JsonProperty("isSuccess")
    private final Boolean isSuccess;
    private final String message;
    private final int code;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private T result;

    // 요청에 성공한 경우 - 결과 값이 있을 때
    public BaseResponse(T result) {
        this.isSuccess = SUCCESS.isSuccess();
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
        this.result = result;
    }
    // 요청에 성공한 경우 - 결과 값이 없을 때
    public BaseResponse() {
        this.isSuccess = SUCCESS.isSuccess();
        this.message = SUCCESS.getMessage();
        this.code = SUCCESS.getCode();
    }


    // 요청에 실패한 경우
    public BaseResponse(BaseResponseStatus status) {
        this.isSuccess = status.isSuccess();
        this.message = status.getMessage();
        this.code = status.getCode();
    }

    @Override
    public String toString() { // 필요할 수도 있음
        String result = "{\n" +
                "  \"isSuccess\": "+this.isSuccess + ",\n"+
                "  \"code\": "+this.code +",\n"+
                "  \"message\": \""+this.message+"\"";
        if (this.result != null) {
            result += ",\n" + "\"result\": \""+this.result.toString()+"\"";
        }
        result += "\n}";
        return result;
    }

}