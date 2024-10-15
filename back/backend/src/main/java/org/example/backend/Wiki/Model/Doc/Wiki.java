package org.example.backend.Wiki.Model.Doc;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.time.LocalDateTime;

@Document(indexName = "wiki")
@Getter
public class Wiki {

    @Id
    private Long id;
    private String title;
    private String content;

    @Field(name = "created_at")
    @JsonProperty("created_at")
    private LocalDateTime createdAt;

    @Field(name = "category_id")
    @JsonProperty("category_id")
    private Long categoryId;

    @Field(name = "category_name")
    @JsonProperty("category_name")
    private String categoryName;

    @Field(name = "thumbnail_img_url")
    @JsonProperty("thumbnail_img_url")
    private String thumbnailImgUrl;

    @Field(name = "input_id")
    private String inputId;


}
