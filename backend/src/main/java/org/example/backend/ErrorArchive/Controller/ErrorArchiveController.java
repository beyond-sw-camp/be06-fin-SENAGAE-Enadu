package org.example.backend.ErrorArchive.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.ErrorArchive.Model.Req.RegisterErrorArchiveReq;
import org.example.backend.ErrorArchive.Service.ErrorArchiveService;
import org.example.backend.Security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/errorarchive")
@RequiredArgsConstructor
public class ErrorArchiveController {
    private final ErrorArchiveService errorArchiveService;
    @PostMapping("/register")
    public BaseResponse<Void> register(
            @RequestBody  RegisterErrorArchiveReq registerErrorArchiveReq,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        errorArchiveService.register(registerErrorArchiveReq, customUserDetails);
        return new BaseResponse<>();
    }
}




