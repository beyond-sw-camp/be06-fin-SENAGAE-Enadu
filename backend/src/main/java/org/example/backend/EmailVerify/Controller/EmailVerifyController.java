package org.example.backend.EmailVerify.Controller;

import lombok.RequiredArgsConstructor;

import org.example.backend.Common.BaseResponse;
import org.example.backend.EmailVerify.Service.EmailVerifyService;
import org.example.backend.Exception.custom.InvalidEmailException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping(value="/email")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:8081")
public class EmailVerifyController {
    private final EmailVerifyService emailVerifyService;

    @GetMapping("/verify")
    public BaseResponse<String> verify(@RequestParam String email, @RequestParam String uuid){
        try {
            emailVerifyService.verifyEmail(email, uuid);
            return new BaseResponse<>("EMAIL VERIFICATION SUCCESS!");
        } catch(InvalidEmailException e){
            // 인증 실패시 에러 메시지 반환
            return new BaseResponse<>(e.getStatus());
        }
    }

}
