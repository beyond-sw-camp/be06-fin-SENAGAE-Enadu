package org.example.backend.User.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidMypageException;
import org.example.backend.Security.CustomUserDetails;
import org.example.backend.User.Model.Res.GetQnaScrapListRes;
import org.example.backend.User.Model.Res.GetUserInfoRes;
import org.example.backend.User.Model.Res.GetUserQnaListRes;
import org.example.backend.User.Service.MypageService;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/mypage")
@RequiredArgsConstructor
public class MypageController {
    private final MypageService mypageService;

    @GetMapping("/info")
    public BaseResponse<GetUserInfoRes> getUserInfo(@AuthenticationPrincipal CustomUserDetails customUserDetails) {
        return new BaseResponse<>(mypageService.getUserInfo(customUserDetails.getUserId()));
    }

    @GetMapping("/log/question")
    public BaseResponse<List<GetUserQnaListRes>> getUserQuestionList(@AuthenticationPrincipal CustomUserDetails customUserDetails, Integer page, Integer size, @RequestParam(value = "userId", required = false) Long userId) {
        if (customUserDetails == null && userId == null) {
            throw new InvalidMypageException(BaseResponseStatus.MYPAGE_NO_USER_ID);
        }
        Long id;
        if (userId != null) {
            id = userId;
        } else {
            id = customUserDetails.getUserId();
        }
        return new BaseResponse<>(mypageService.getUserQnaList(id, page, size, "question"));
    }

    @GetMapping("/log/answer")
    public BaseResponse<List<GetUserQnaListRes>> getUserAnswerList(@AuthenticationPrincipal CustomUserDetails customUserDetails, Integer page, Integer size, @RequestParam(value = "userId", required = false) Long userId) {
        if (customUserDetails == null && userId == null) {
            throw new InvalidMypageException(BaseResponseStatus.MYPAGE_NO_USER_ID);
        }
        Long id;
        if (userId != null) {
            id = userId;
        } else {
            id = customUserDetails.getUserId();
        }
        return new BaseResponse<>(mypageService.getUserQnaList(id, page, size, "answer"));
    }

    @GetMapping("/scrap/qna")
    public BaseResponse<List<GetQnaScrapListRes>> getQnaScrapList(@AuthenticationPrincipal CustomUserDetails customUserDetails, Integer page, Integer size) {
        return new BaseResponse<>(mypageService.getQnaScrapList(customUserDetails.getUserId(), page, size));
    }
}
