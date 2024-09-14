package org.example.backend.ErrorArchive.Model.Req;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ListErrorArchiveReq {
    private Integer page;
    private Integer size;
}
