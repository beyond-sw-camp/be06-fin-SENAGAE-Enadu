package org.example.backend.ErrorArchive.Model.Req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LikeOrHateReq {
    private Long id;
    private Boolean isLike;
}
