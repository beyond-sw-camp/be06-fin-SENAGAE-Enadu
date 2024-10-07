package org.example.backend.Qna.Service;

import org.example.backend.Qna.model.Res.GetQnaListRes;
import org.example.backend.Qna.model.req.GetQnaSearchReq;

import java.util.List;

public interface QnaSearchService {
    public List<GetQnaListRes> getQnaSearch(GetQnaSearchReq getQnaSearchReq);
}
