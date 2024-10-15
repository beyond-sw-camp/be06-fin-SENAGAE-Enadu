package org.example.backend.Wiki.Service;

import org.example.backend.Wiki.Model.Req.GetWikiSearchReq;
import org.example.backend.Wiki.Model.Res.WikiListRes;

import java.io.IOException;
import java.util.List;

public interface WikiSearchService {
    List<WikiListRes> search(GetWikiSearchReq getWikiSearchReq) throws IOException;
}
