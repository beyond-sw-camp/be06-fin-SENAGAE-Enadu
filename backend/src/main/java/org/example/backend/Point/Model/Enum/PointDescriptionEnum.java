package org.example.backend.Point.Model.Enum;

import lombok.Getter;

@Getter
public enum PointDescriptionEnum {

    POINT_QNA_WRITE("질문글 작성", 10),
    POINT_WIKI_WRITE("위키 작성", 15),
    POINT_WIKI_UPDATE("위키 수정", 15),
    POINT_ERRORARCHIVE_WRITE("에러 게시판 작성", 30),
    POINT_QNA_ANSWER_WRITE("질문 글 답변 작성", 20),
    POINT_QNA_ANSWER_ACCEPT("본인 답변 채택", 50),
    POINT_ERRORARCHIVE_RECOMMEND("에러아카이브 추천 10 이상", 10),
    POINT_ERRORARCHIVE_RECOMMEND_CANCEL("에러아카이브 추천 10 이상 취소", -10),
    POINT_ERRORARCHIVE_DISRECOMMEND("에러아카이브 비추천 5 이상", -5),
    POINT_ERRORARCHIVE_DISRECOMMEND_CANCEL("에러아카이브 비추천 5 이상 취소", 5),
    POINT_ANSWER_RECOMMEND("답변 추천 10 이상", 10),
    POINT_ANSWER_RECOMMEND_CANCEL("답변 추천 10 이상 취소", -10),
    POINT_ANSWER_DISRECOMMEND("답변 비추천 5 이상", -5),
    POINT_ANSWER_DISRECOMMEND_CANCEL("답변 비추천 5 이상 취소", 5);

    private final String description;
    private final int value;

    PointDescriptionEnum(String description, int value) {
        this.description = description;
        this.value = value;
    }
}
