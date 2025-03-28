package org.example.backend.EmailVerify.Controller;

import lombok.RequiredArgsConstructor;

import org.example.backend.Common.BaseResponse;
import com.example.common.EmailVerify.Model.Entity.EmailVerify;
import org.example.backend.EmailVerify.Service.EmailVerifyService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value="/email")
@RequiredArgsConstructor
public class EmailVerifyController {
    private final EmailVerifyService emailVerifyService;

    @PostMapping("/verify")
    public BaseResponse<String> verify(@RequestBody EmailVerify emailVerify){
            String email = emailVerify.getEmail();
            String uuid = emailVerify.getUuid();
            emailVerifyService.verifyEmail(email, uuid);
            return new BaseResponse<>("EMAIL VERIFICATION SUCCESS!");
    }

}
