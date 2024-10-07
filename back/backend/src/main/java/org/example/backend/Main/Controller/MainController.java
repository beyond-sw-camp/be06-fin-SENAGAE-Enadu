package org.example.backend.Main.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Main.Model.Res.MainRes;
import org.example.backend.Main.Service.MainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;

    @GetMapping("/main")
    public BaseResponse<MainRes> getMainPageInfo(@RequestParam(defaultValue = "5") Integer errorArchiveSize,
                                                 @RequestParam(defaultValue = "4") Integer wikiSize,
                                                 @RequestParam(defaultValue = "5") Integer qnaSize) {
        return new BaseResponse<>(mainService.getMainInfo(errorArchiveSize, wikiSize, qnaSize));
    }
}
