package org.example.backend.Main.Model.Res;

import lombok.Builder;
import lombok.Getter;
import org.example.backend.ErrorArchive.Model.Res.ListErrorArchiveRes;
import org.example.backend.Qna.model.Res.GetQnaListRes;
import org.example.backend.Wiki.Model.Res.WikiListRes;

import java.time.LocalDateTime;
import java.util.List;

@Builder
@Getter
public class GetMainSearchRes {
    List<GetQnaListRes> qnaListResList;
    List<ListErrorArchiveRes> errorArchiveResList;
    List<WikiListRes> wikiListResList;
}
