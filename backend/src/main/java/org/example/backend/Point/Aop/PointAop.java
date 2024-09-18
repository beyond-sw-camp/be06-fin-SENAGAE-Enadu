package org.example.backend.Point.Aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.example.backend.Point.Model.Enum.PointDescriptionEnum;
import org.example.backend.Point.Service.PointService;
import org.example.backend.Security.CustomUserDetails;
import org.springframework.stereotype.Component;

@Aspect
@Component
@RequiredArgsConstructor
public class PointAop {
    private final PointService pointService;

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

    //todo - 질문 글 답변 작성, 답변 채택, 추천 10 이상, 비추천 5이상
}
