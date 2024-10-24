package org.example.backend.Qna.model.Doc;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.time.LocalDateTime;

@Document(indexName = "qna_board")
@Getter
public class QnaBoard {

    @Id
    private Long id;

    private String title;

    private String content;

    @Field(name = "answer_cnt")
    @JsonProperty("answer_cnt")
    private Integer answerCnt;

    @Field(name = "like_cnt")
    @JsonProperty("like_cnt")
    private Integer likeCnt;

    @Field(name = "created_at")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    private Boolean enable;

    @Field(name = "sub_category_id")
    @JsonProperty("sub_category_id")
    private Long subCategoryId;

    @Field(name = "sub_category_name")
    @JsonProperty("sub_category_name")
    private String subCategoryName;

    @Field(name = "super_category_id")
    @JsonProperty("super_category_id")
    private Long superCategoryId;

    @Field(name = "super_category_name")
    @JsonProperty("super_category_name")
    private String superCategoryName;

    private String nickname;

    @Field(name = "profile_img")
    @JsonProperty("profile_img")
    private String profileImg;

    private String grade;

    @Field(name = "input_id")
    @JsonProperty("input_id")
    private String inputId;

}
