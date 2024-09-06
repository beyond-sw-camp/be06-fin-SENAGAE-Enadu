package org.example.backend.User.Model.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.example.backend.Answer.Model.Entity.Answer;
import org.example.backend.Answer.Model.Entity.AnswerComment;
import org.example.backend.Answer.Model.Entity.AnswerLike;
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
    private String name;
    @Column(nullable = false, length = 50)
    private String nickname;
    @Column(nullable = false)
    private String profileImg;
    @Column(nullable = false, length = 50)
    private String grade;
    @Column(nullable = false)
    private Integer point;
    @Column(nullable = false)
    private Boolean verify;
    @Column(nullable = false)
    private LocalDateTime createdAt;
    @Column(nullable = false)
    private LocalDateTime modifiedAt;
    @Column(nullable = false)
    private Boolean enable;
    @Column(nullable = false, length = 20)
    private String type;

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
}
