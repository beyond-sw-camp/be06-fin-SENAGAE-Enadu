package org.example.backend.User.Model.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.example.backend.Qna.model.Entity.Answer;
import org.example.backend.Qna.model.Entity.AnswerComment;
import org.example.backend.Qna.model.Entity.AnswerLike;
import org.example.backend.Chat.Model.Entity.Chat;
import org.example.backend.ErrorArchive.Model.Entity.ErrorArchive;
import org.example.backend.ErrorArchive.Model.Entity.ErrorLike;
import org.example.backend.ErrorArchive.Model.Entity.ErrorScrap;
import org.example.backend.Point.Model.Entity.PointDetail;
import org.example.backend.Qna.model.Entity.QnaBoard;
import org.example.backend.Qna.model.Entity.QnaScrap;
import org.example.backend.Qna.model.Entity.QnaLike;
import org.example.backend.Wiki.Model.Entity.WikiContent;
import org.example.backend.Wiki.Model.Entity.WikiScrap;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String email;

    private String password;

    @Column(nullable = false, length = 50)
    private String nickname;

    @Builder.Default
    @Column(nullable = false)
    private String profileImg = "https://dayun2024-s3.s3.ap-northeast-2.amazonaws.com/IMAGE/2024/09/11/0d7ca962-ccee-4fbb-9b5d-f5deec5808c6";

    @Builder.Default
    @Column(nullable = false, length = 50)
    private String grade = "뉴비";

    @Builder.Default
    @Column(nullable = false)
    private Integer point = 10;

    @Builder.Default
    @Column(nullable = false)
    private Boolean verify = false;

    @CreatedDate
    @Column(nullable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(nullable = false)
    private LocalDateTime modifiedAt;

    @Builder.Default
    @Column(nullable = false)
    private Boolean enable = true;

    @Builder.Default
    @Column(nullable = false, length = 20)
    private String type = "InApp";

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<PointDetail> pointDetails;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Chat> chatList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<QnaLike> qnaLikeList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<QnaBoard> qnaBoardList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<QnaScrap> qnaScrapList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<Answer> answerList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<AnswerLike> answerLikeList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<AnswerComment> answerCommentList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<WikiContent> wikiContentList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<WikiScrap> wikiScrapList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<ErrorArchive> errorArchiveList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<ErrorLike> errorLikeList;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private List<ErrorScrap> ErrorScrapList;

    public void updateNickname(String nickname) {
        this.nickname = nickname;
    }

    public void updateProfileImg(String imgUrl) {
        this.profileImg = imgUrl;
    }
}
