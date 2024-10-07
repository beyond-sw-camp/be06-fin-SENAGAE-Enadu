package org.example.backend.ErrorArchive.Model.Req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class GetErrorArchiveSearchReq {
    private Integer page = 0; // 프론트에서 값을 지정해주지 않으면 자동으로 지정
    private Integer size = 16;
    private String sort = "latest";
    private String type = "tc"; // tc: 제목+내용 , t: 제목, c: 내용 으로 검색
    private String keyword = ""; // keyword가 안들어오면 빈문자열로 처리됨(나중에 편함)
    private Long categoryId;
}
