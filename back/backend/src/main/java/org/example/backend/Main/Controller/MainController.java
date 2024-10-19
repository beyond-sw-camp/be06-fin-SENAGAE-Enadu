package org.example.backend.Main.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidMainException;
import org.example.backend.Main.Model.Res.GetMainSearchRes;
import org.example.backend.Main.Model.Res.MainRes;
import org.example.backend.Main.Service.MainElasticSearchService;
import org.example.backend.Main.Service.MainService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


@RestController
@RequiredArgsConstructor
public class MainController {
    private final MainService mainService;
    private final MainElasticSearchService mainElasticSearchService;

    @GetMapping("/main")
    public BaseResponse<MainRes> getMainPageInfo(@RequestParam(defaultValue = "5") Integer errorArchiveSize,
                                                 @RequestParam(defaultValue = "4") Integer wikiSize,
                                                 @RequestParam(defaultValue = "5") Integer qnaSize) {
        return new BaseResponse<>(mainService.getMainInfo(errorArchiveSize, wikiSize, qnaSize));
    }
    @GetMapping("/main/search")
    public BaseResponse<GetMainSearchRes> search(@RequestParam(defaultValue = "8") Integer errorArchiveSize,
                                                 @RequestParam(defaultValue = "4") Integer wikiSize,
                                                 @RequestParam(defaultValue = "8") Integer qnaSize,
                                                String keyword) {
        try {
            return new BaseResponse<>(mainElasticSearchService.mainSearch(wikiSize,errorArchiveSize,qnaSize,keyword));
        } catch (IOException e) {
            throw new InvalidMainException(BaseResponseStatus.MAIN_SEARCH_EMPTY_REQUEST);
        }
    }
}
