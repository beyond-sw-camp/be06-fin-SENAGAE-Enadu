package org.example.backend.ErrorArchive.Model.Doc;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Id;
import lombok.Getter;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.time.LocalDateTime;

@Document(indexName = "error_archive")
@Getter
public class ErrorArchive {
    @Id
    private Long id;

    @Field(name = "created_at")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    private String grade;

    @Field(name = "input_id")
    @JsonProperty("input_id")
    private String inputId;

    @Field(name = "like_cnt")
    @JsonProperty("like_cnt")
    private Integer likeCnt;

    private boolean enable;
    @Field(name = "super_category_id")
    @JsonProperty("super_category_id")
    private Long superCategoryId;

    @Field(name = "sub_category_id")
    @JsonProperty("sub_category_id")
    private Long subCategoryId;

    private String title;
    private String content;

    @Field(name = "super_category_name")
    @JsonProperty("super_category_name")
    private String superCategoryName;


    @Field(name = "sub_category_name")
    @JsonProperty("sub_category_name")
    private String subCategoryName;

    private String nickname;
    @Field(name = "profile_img")
    @JsonProperty("profile_img")
    private String profileImg;

}
