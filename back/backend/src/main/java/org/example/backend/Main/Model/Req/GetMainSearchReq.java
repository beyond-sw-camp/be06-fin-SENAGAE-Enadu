package org.example.backend.Main.Model.Req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetMainSearchReq {
    private Integer page = 0;
    private Integer size = 16;
    private String sort = "latest";
    private String type = "tc";
    private String keyword = "";
    private Long categoryId;
}
