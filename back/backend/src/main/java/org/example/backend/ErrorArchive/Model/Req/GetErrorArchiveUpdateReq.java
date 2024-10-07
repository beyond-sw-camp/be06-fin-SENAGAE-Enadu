package org.example.backend.ErrorArchive.Model.Req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetErrorArchiveUpdateReq {
    private Long id;
    private String content;
    private String title;
    private Long categoryId;
}
