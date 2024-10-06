package org.example.backend.Point.Aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.example.backend.ErrorArchive.Model.Entity.ErrorArchive;
import org.example.backend.ErrorArchive.Model.Res.LikeOrHateRes;
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
    private ThreadLocal<Integer> beforeErrorArchiveLikeCount = new ThreadLocal<>();
    private ThreadLocal<Integer> beforeErrorArchiveHateCount = new ThreadLocal<>();


    @AfterReturning("execution(* org.example.backend.Qna.Service.QnaService.saveQuestion(..))")
    public void givePointAfterSuccessQnaRegister(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        pointService.givePoint((Long) args[1], PointDescriptionEnum.POINT_QNA_WRITE);
    }

    @AfterReturning("execution(* org.example.backend.Wiki.Service.WikiService.register(..))")
    public void givePointAfterSuccessWikiRegister(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        pointService.givePoint(((CustomUserDetails) args[2]).getUserId(), PointDescriptionEnum.POINT_WIKI_WRITE);
    }

    @AfterReturning("execution(* org.example.backend.ErrorArchive.Service.ErrorArchiveService.register(..))")
    public void givePointAfterSuccessErrorArchiveRegister(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        pointService.givePoint(((CustomUserDetails) args[1]).getUserId(), PointDescriptionEnum.POINT_ERRORARCHIVE_WRITE);
    }

    @AfterReturning("execution(* org.example.backend.Qna.Service.QnaService.saveAnswer(..))")
    public void givePointAfterSuccessAnswerRegister(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        pointService.givePoint((Long) args[1], PointDescriptionEnum.POINT_QNA_ANSWER_WRITE);
    }

    @AfterReturning("execution(* org.example.backend.Qna.Service.QnaService.adoptedAnswer(..))")
    public void givePointAfterSuccessAnswerAdopt(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long answerId = (Long) args[1];
        Answer answer = answerRepository.findById(answerId).get(); //AfterReturning이므로 무조건 있음
        Long userId = answer.getUser().getId();
        pointService.givePoint(userId, PointDescriptionEnum.POINT_QNA_ANSWER_ACCEPT);
    }

    @Before("execution(* org.example.backend.ErrorArchive.Service.ErrorArchiveService.toggleErrorArchiveLikeOrHate(..))")
    public void beforeErrorArchiveLikeOrHate(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        Long errorArchiveId = (Long) args[0];
        Optional<ErrorArchive> optionalErrorArchive = errorArchiveReository.findById(errorArchiveId);
        if (optionalErrorArchive.isEmpty()) {
            return;
        }
        ErrorArchive errorArchive = optionalErrorArchive.get();
        beforeErrorArchiveLikeCount.set(errorArchive.getLikeCount());
        beforeErrorArchiveHateCount.set(errorArchive.getHateCount());
    }

    @AfterReturning(pointcut = "execution(* org.example.backend.ErrorArchive.Service.ErrorArchiveService.toggleErrorArchiveLikeOrHate(..))",
            returning = "result")
    public void givePointAfterSuccessErrorArchiveLike(JoinPoint joinPoint, LikeOrHateRes result) {
        Object[] args = joinPoint.getArgs();
        Long errorArchiveId = (Long) args[0];

        Optional<ErrorArchive> optionalErrorArchive = errorArchiveReository.findById(errorArchiveId);
        if (optionalErrorArchive.isEmpty()) {
            return;
        }
        ErrorArchive errorArchive = optionalErrorArchive.get();
        Long userId = errorArchive.getUser().getId();

        if (beforeErrorArchiveLikeCount.get() == 9 && errorArchive.getLikeCount() == 10) {
            pointService.givePoint(userId, PointDescriptionEnum.POINT_ERRORARCHIVE_RECOMMEND);
        } else if (beforeErrorArchiveLikeCount.get() == 10 && errorArchive.getLikeCount() == 9) {
            pointService.givePoint(userId, PointDescriptionEnum.POINT_ERRORARCHIVE_RECOMMEND_CANCEL);
        }

        if (beforeErrorArchiveHateCount.get() == 4 && errorArchive.getHateCount() == 5) {
            pointService.givePoint(userId, PointDescriptionEnum.POINT_ERRORARCHIVE_DISRECOMMEND);
        } else if (beforeErrorArchiveHateCount.get() == 5 && errorArchive.getHateCount() == 4) {
            pointService.givePoint(userId, PointDescriptionEnum.POINT_ERRORARCHIVE_DISRECOMMEND_CANCEL);
        }

    }

    //todo -  추천 10 이상, 비추천 5이상
}
