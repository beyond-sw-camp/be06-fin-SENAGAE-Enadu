package org.example.backend.ErrorArchive.Controller;

import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponse;
import org.example.backend.ErrorArchive.Model.Req.ListErrorArchiveReq;
import org.example.backend.ErrorArchive.Model.Req.RegisterErrorArchiveReq;
import org.example.backend.ErrorArchive.Model.Res.ListErrorArchiveRes;
import org.example.backend.ErrorArchive.Model.Res.RegisterErrorArchiveRes;
import org.example.backend.ErrorArchive.Service.ErrorArchiveService;
import org.example.backend.Security.CustomUserDetails;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/errorarchive")
@RequiredArgsConstructor
public class ErrorArchiveController {

    private final ErrorArchiveService errorArchiveService;

    @PostMapping()
    public BaseResponse<RegisterErrorArchiveRes> register(
            @RequestBody RegisterErrorArchiveReq registerErrorArchiveReq,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        RegisterErrorArchiveRes registerErrorArchiveRes = errorArchiveService.register(registerErrorArchiveReq, customUserDetails);
        return new BaseResponse<>(registerErrorArchiveRes);

        }
    // 아카이브 목록 조회
    @GetMapping("/list")
    public BaseResponse<List<ListErrorArchiveRes>> list(ListErrorArchiveReq listErrorArchiveReq) {
        if(listErrorArchiveReq.getPage() == null) {
            listErrorArchiveReq.setPage(0);
        }
        if(listErrorArchiveReq.getSize() == null || listErrorArchiveReq.getSize() == 0){
            listErrorArchiveReq.setSize(15);
        }
        List<ListErrorArchiveRes> errorArchiveList = errorArchiveService.errorArchiveList(listErrorArchiveReq);
        return new BaseResponse<>(errorArchiveList);
    }

    }





