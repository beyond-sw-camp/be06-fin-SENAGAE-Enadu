package com.example.common.User.Model.Entity;

import com.example.common.Chat.Model.Entity.Chat;
import com.example.common.ErrorArchive.Model.Entity.ErrorArchive;
import com.example.common.ErrorArchive.Model.Entity.ErrorLike;
import com.example.common.ErrorArchive.Model.Entity.ErrorScrap;
import com.example.common.Point.Model.Entity.PointDetail;
import com.example.common.Qna.model.Entity.*;
import com.example.common.Ranking.model.Entity.DailyRanking;
import com.example.common.Ranking.model.Entity.WeeklyRanking;
import com.example.common.Wiki.Model.Entity.WikiContent;
import com.example.common.Wiki.Model.Entity.WikiScrap;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Table(name = "user")
@EntityListeners(AuditingEntityListener.class)
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
    @Column(name = "profile_img")
    private String profileImg = "https://dayun2024-s3.s3.ap-northeast-2.amazonaws.com/IMAGE/2024/09/11/0d7ca962-ccee-4fbb-9b5d-f5deec5808c6";

    @Builder.Default
    @Column(nullable = false, length = 50)
    private String grade = "뉴비";

    @Builder.Default
    @Column(nullable = false)
    private Integer point = 10;

    @Builder.Default
    @Column(name = "weekly_point", nullable = false)
    private Integer weeklyPoint = 0;

    @Builder.Default
    @Column(nullable = false)
    private Boolean verify = false;

    @CreatedDate
    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "modified_at")
    private LocalDateTime modifiedAt;

    @Builder.Default
    @Column(nullable = false)
    private Boolean enable = true;

    @Builder.Default
    @Column(nullable = false, length = 20)
    private String type = "InApp";

    @Column(name = "point_detail")
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

    public void updatePoint(Integer point) {
        this.point += point;
        this.weeklyPoint += point;
    }

    public void updateGrade(String grade) {
        this.grade = grade;
    }

    public void updatePassword(String password) {
        this.password = password;
    }

    public void updateEnable(Boolean enable) {
        this.enable = enable;
    }
    public void updateVerified(boolean verify){ this.verify =verify; }

}
