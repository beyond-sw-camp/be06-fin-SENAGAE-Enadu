package org.example.backend.Point.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Point.Model.Res.GetPointHistoryRes;
import org.example.backend.Point.Service.PointService;
import org.example.backend.Security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class PointController {
    private final PointService pointService;

    @GetMapping("/point")
    public BaseResponse<List<GetPointHistoryRes>> getPointHistory(@AuthenticationPrincipal CustomUserDetails customUserDetails, Integer page, Integer size){
        return new BaseResponse<>(pointService.getPointHistory(customUserDetails.getUserId(), page, size));
    }


}
