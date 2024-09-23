package org.example.backend.ErrorArchive.Service;


import org.example.backend.ErrorArchive.Model.Req.GetErrorArchiveSearchReq;
import org.example.backend.ErrorArchive.Model.Res.ListErrorArchiveRes;

import java.util.List;

public interface ErrorArchiveSearchService {
    List<ListErrorArchiveRes> errorArchiveSearch(GetErrorArchiveSearchReq errorArchiveSearchReq);
}
