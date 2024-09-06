package org.example.backend.ErrorArchive.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorArchive {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "errorArchive", fetch = FetchType.LAZY)
    private List<ErrorScrap> errorScrapList; // bigint

    @OneToMany(mappedBy = "errorArchive", fetch = FetchType.LAZY)
    private List<ErrorScrap> errorLikeList; // bigint
    //@ManyToOne
    //@JoinColumn
    //private User userId; // bigint

    @Column(name = "category_id", nullable = false)
    private Long categoryId; // bigint

    @Column(name = "qna_board_id", nullable = false)
    private Long qnaBoardId; // bigint

    @Column(name = "title", length = 100, nullable = false)
    private String title; // varchar(100)

    @Column(name = "content", columnDefinition = "TEXT", nullable = false)
    private String content; // text

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt; // datetime

    @Column(name = "modified_at", nullable = false)
    private LocalDateTime modifiedAt; // datetime (NULL 가능)

    @Column(name = "enable", nullable = false)
    private boolean enable; // boolean

    @Column(name = "like_cnt", nullable = false)
    private int likeCount; // int

    @Column(name = "hate_cnt", nullable = false)
    private int hateCount; // int



}
