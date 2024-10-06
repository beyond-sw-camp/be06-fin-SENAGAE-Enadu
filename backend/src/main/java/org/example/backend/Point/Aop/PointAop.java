package org.example.backend.Point.Aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.backend.ErrorArchive.Model.Entity.ErrorArchive;
import org.example.backend.ErrorArchive.Repository.ErrorArchiveReository;
import org.example.backend.Point.Model.Enum.PointDescriptionEnum;
import org.example.backend.Point.Service.PointService;
import org.example.backend.Qna.Repository.AnswerRepository;
import org.example.backend.Qna.model.Entity.Answer;
import org.example.backend.Security.CustomUserDetails;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Aspect
@Component
@RequiredArgsConstructor
public class PointAop {
    private final PointService pointService;
    private final AnswerRepository answerRepository;
    private final ErrorArchiveReository errorArchiveReository;
    private final ThreadLocal<Integer> beforeLikeCount = new ThreadLocal<>();
    private final ThreadLocal<Integer> beforeHateCount = new ThreadLocal<>();


    // QnA 작성
    @AfterReturning("execution(* org.example.backend.Qna.Service.QnaService.saveQuestion(..))")
    public void givePointAfterSuccessQnaRegister(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        pointService.givePoint((Long) args[1], PointDescriptionEnum.POINT_QNA_WRITE);
    }

    // 위키 작성
    @AfterReturning("execution(* org.example.backend.Wiki.Service.WikiService.register(..)) || " +
            "execution(* org.example.backend.Wiki.Service.WikiService.update(..))")
    public void givePointAfterSuccessWikiRegisterAndUpdate(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        if (joinPoint.getSignature().getName().equals("register")) {
            pointService.givePoint(((CustomUserDetails) args[2]).getUserId(), PointDescriptionEnum.POINT_WIKI_WRITE);
        } else {
            pointService.givePoint((Long) args[2], PointDescriptionEnum.POINT_WIKI_UPDATE);
        }
    }

    // 에러아카이브 작성
    @AfterReturning("execution(* org.example.backend.ErrorArchive.Service.ErrorArchiveService.register(..))")
    public void givePointAfterSuccessErrorArchiveRegister(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        pointService.givePoint(((CustomUserDetails) args[1]).getUserId(), PointDescriptionEnum.POINT_ERRORARCHIVE_WRITE);
    }

    // 답변 등록
    @AfterReturning("execution(* org.example.backend.Qna.Service.QnaService.saveAnswer(..))")
    public void givePointAfterSuccessAnswerRegister(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        pointService.givePoint((Long) args[1], PointDescriptionEnum.POINT_QNA_ANSWER_WRITE);
    }

    // 답변 채택
    @AfterReturning("execution(* org.example.backend.Qna.Service.QnaService.adoptedAnswer(..))")
    public void givePointAfterSuccessAnswerAdopt(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long answerId = (Long) args[1];
        Answer answer = answerRepository.findById(answerId).get(); //AfterReturning이므로 무조건 있음
        Long userId = answer.getUser().getId();
        pointService.givePoint(userId, PointDescriptionEnum.POINT_QNA_ANSWER_ACCEPT);
    }

    // 에러아카이브 좋아요/싫어요
    @Before("execution(* org.example.backend.ErrorArchive.Service.ErrorArchiveService.toggleErrorArchiveLikeOrHate(..))")
    public void beforeErrorArchiveLikeOrHate(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long errorArchiveId = (Long) args[0];
        Optional<ErrorArchive> optionalErrorArchive = errorArchiveReository.findById(errorArchiveId);
        if (optionalErrorArchive.isEmpty()) {
            return;
        }
        ErrorArchive errorArchive = optionalErrorArchive.get();
        beforeLikeCount.set(errorArchive.getLikeCount());
        beforeHateCount.set(errorArchive.getHateCount());
    }

    @AfterReturning("execution(* org.example.backend.ErrorArchive.Service.ErrorArchiveService.toggleErrorArchiveLikeOrHate(..))")
    public void givePointAfterSuccessErrorArchiveLikeOrHate(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long errorArchiveId = (Long) args[0];

        Optional<ErrorArchive> optionalErrorArchive = errorArchiveReository.findById(errorArchiveId);
        if (optionalErrorArchive.isEmpty()) {
            return;
        }
        ErrorArchive errorArchive = optionalErrorArchive.get();
        Long userId = errorArchive.getUser().getId();

        if (beforeLikeCount.get() == 9 && errorArchive.getLikeCount() == 10) {
            pointService.givePoint(userId, PointDescriptionEnum.POINT_ERRORARCHIVE_RECOMMEND);
        } else if (beforeLikeCount.get() == 10 && errorArchive.getLikeCount() == 9) {
            pointService.givePoint(userId, PointDescriptionEnum.POINT_ERRORARCHIVE_RECOMMEND_CANCEL);
        }

        if (beforeHateCount.get() == 4 && errorArchive.getHateCount() == 5) {
            pointService.givePoint(userId, PointDescriptionEnum.POINT_ERRORARCHIVE_DISRECOMMEND);
        } else if (beforeHateCount.get() == 5 && errorArchive.getHateCount() == 4) {
            pointService.givePoint(userId, PointDescriptionEnum.POINT_ERRORARCHIVE_DISRECOMMEND_CANCEL);
        }
        beforeLikeCount.remove();
        beforeHateCount.remove();
    }

    // 답변 좋아요/싫어요
    @Before("execution(* org.example.backend.Qna.Service.QnaService.checkAnswerLike(..)) || " +
            "execution(* org.example.backend.Qna.Service.QnaService.checkAnswerHate(..)))")
    public void beforeAnswerLikeOrHate(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long answerId = (Long) args[1];
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        if (optionalAnswer.isEmpty()) {
            return;
        }
        Answer answer = optionalAnswer.get();
        beforeLikeCount.set(answer.getLikeCount());
        beforeHateCount.set(answer.getHateCount());
    }

    @AfterReturning("execution(* org.example.backend.Qna.Service.QnaService.checkAnswerLike(..)) || " +
            "execution(* org.example.backend.Qna.Service.QnaService.checkAnswerHate(..)))")
    public void givePointAfterSuccessAnswerLikeOrHate(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long answerId = (Long) args[1];
        Optional<Answer> optionalAnswer = answerRepository.findById(answerId);
        if (optionalAnswer.isEmpty()) {
            return;
        }
        Answer answer = optionalAnswer.get();
        Long userId = answer.getUser().getId();

        if (beforeLikeCount.get() == 9 && answer.getLikeCount() == 10) {
            pointService.givePoint(userId, PointDescriptionEnum.POINT_ANSWER_RECOMMEND);
        } else if (beforeLikeCount.get() == 10 && answer.getLikeCount() == 9) {
            pointService.givePoint(userId, PointDescriptionEnum.POINT_ANSWER_RECOMMEND_CANCEL);
        }

        if (beforeHateCount.get() == 4 && answer.getHateCount() == 5) {
            pointService.givePoint(userId, PointDescriptionEnum.POINT_ANSWER_DISRECOMMEND);
        } else if (beforeHateCount.get() == 5 && answer.getHateCount() == 4) {
            pointService.givePoint(userId, PointDescriptionEnum.POINT_ANSWER_DISRECOMMEND_CANCEL);
        }

        beforeLikeCount.remove();
        beforeHateCount.remove();
    }


    //todo -  추천 10 이상, 비추천 5이상
}
