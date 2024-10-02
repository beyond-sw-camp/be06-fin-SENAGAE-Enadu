package org.example.backend.ErrorArchive.Model.Req;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ListErrorArchiveReq {
    private String sort;
    private Integer page;
    private Integer size;
}
