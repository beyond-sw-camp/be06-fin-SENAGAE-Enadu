package org.example.backend.Point.Service;

import com.example.common.Point.Repository.PointRepository;
import com.example.common.Ranking.Repository.DailyRankingRepository;
import com.example.common.Ranking.Repository.WeeklyRankingRepository;
import com.example.common.Ranking.model.Entity.DailyRanking;
import com.example.common.Ranking.model.Entity.WeeklyRanking;
import com.example.common.User.Model.Entity.User;
import com.example.common.User.Repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidUserException;
import com.example.common.Point.Model.Entity.PointDetail;
import org.example.backend.Point.Model.Enum.PointDescriptionEnum;
import org.example.backend.Point.Model.Res.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class PointService {
    private final PointRepository pointRepository;
    private final UserRepository userRepository;
    private final DailyRankingRepository dailyRankingRepository;
    private final WeeklyRankingRepository weeklyRankingRepository;

    public List<GetPointHistoryRes> getPointHistory(Long userId, Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<PointDetail> pointDetailPage = pointRepository.findAllByUserId(userId, pageable);
        List<GetPointHistoryRes> getPointHistoryResList = new ArrayList<>();
        for (PointDetail pointDetail : pointDetailPage) {
            getPointHistoryResList.add(GetPointHistoryRes.builder()
                    .point(pointDetail.getPoint())
                    .state(pointDetail.isState())
                    .description(pointDetail.getDescription())
                    .createdAt(pointDetail.getCreatedAt())
                    .totalPage(pointDetailPage.getTotalPages())
                    .build());
        }
        return getPointHistoryResList;
    }

    public GetMyRankRes getMyRank(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
        return GetMyRankRes.builder()
                .grade(user.getGrade())
                .point(user.getPoint())
                .dailyRanking(dailyRankingRepository.findByUserId(userId).getRank())
                .weeklyRanking(weeklyRankingRepository.findByUserId(userId).getRank())
                .build();
    }

    public List<GetRankingRes> getDailyRankingList(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<DailyRanking> dailyRankingPage = dailyRankingRepository.findAll(pageable);
        List<GetRankingRes> getRankingResList = new ArrayList<>();
        for (DailyRanking dailyRanking : dailyRankingPage) {
            getRankingResList.add(GetRankingRes.builder()
                    .point(dailyRanking.getPoint())
                    .rank(dailyRanking.getRank())
                    .grade(dailyRanking.getUser().getGrade())
                    .nickname(dailyRanking.getUser().getNickname())
                    .profileImg(dailyRanking.getUser().getProfileImg())
                    .totalPage(dailyRankingPage.getTotalPages())
                    .build());
        }
        return getRankingResList;
    }

    public List<GetRankingRes> getWeeklyRankingList(Integer page, Integer size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<WeeklyRanking> weeklyRankingPage = weeklyRankingRepository.findAll(pageable);
        List<GetRankingRes> getRankingResList = new ArrayList<>();
        for (WeeklyRanking weeklyRanking : weeklyRankingPage) {
            getRankingResList.add(GetRankingRes.builder()
                    .point(weeklyRanking.getDeltaPoint())
                    .rank(weeklyRanking.getRank())
                    .grade(weeklyRanking.getUser().getGrade())
                    .nickname(weeklyRanking.getUser().getNickname())
                    .profileImg(weeklyRanking.getUser().getProfileImg())
                    .totalPage(weeklyRankingPage.getTotalPages())
                    .build());
        }
        return getRankingResList;
    }

    @Transactional
    public void givePoint(Long userId, PointDescriptionEnum pointDescriptionEnum) {
        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND));
        user.updatePoint(pointDescriptionEnum.getValue());
        user.updateGrade(getGradeByPoint(user.getPoint()));

        PointDetail pointDetail = PointDetail.builder()
                .point(Math.abs(pointDescriptionEnum.getValue()))
                .description(pointDescriptionEnum.getDescription())
                .user(user)
                .state(pointDescriptionEnum.getValue() > 0)
                .build();

        userRepository.save(user);
        pointRepository.save(pointDetail);
    }

    private String getGradeByPoint(Integer point) {
        if (point >= 3000) {
            return "신";
        } else if (point >= 1000) {
            return "마스터";
        } else if (point >= 500) {
            return "프로";
        } else if (point >= 100) {
            return "견습";
        } else {
            return "뉴비";
        }
    }
}
