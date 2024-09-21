package org.example.backend.EmailVerify.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.EmailVerify.Model.Entity.EmailVerify;
import org.example.backend.EmailVerify.Service.EmailVerifyService;
import org.example.backend.Exception.custom.InvalidEmailException;
import org.example.backend.User.Service.UserService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/email")
@RequiredArgsConstructor
public class EmailVerifyController {
    private final EmailVerifyService emailVerifyService;

    @PostMapping("/verify")
    public BaseResponse<String> verify(@RequestParam String email,@RequestParam String uuid){
        try {
            emailVerifyService.verifyEmail(email, uuid);
            return new BaseResponse<>("EMAIL VERIFICATION SUCCESS!");
        } catch(InvalidEmailException e){
            // 인증 실패시 에러 메시지 반환
            return new BaseResponse<>(e.getStatus());
        }
    }
}
