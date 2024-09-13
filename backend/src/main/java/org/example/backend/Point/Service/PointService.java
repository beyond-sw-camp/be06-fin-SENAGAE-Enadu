package org.example.backend.Point.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Point.Model.Entity.PointDetail;
import org.example.backend.Point.Model.Res.GetPointHistoryRes;
import org.example.backend.Point.Repository.PointRepository;
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

    public List<GetPointHistoryRes> getPointHistory(Long userId, Integer page, Integer size){
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<PointDetail> pointDetailPage = pointRepository.findAllByUserId(userId, pageable);
        List<GetPointHistoryRes> getPointHistoryResList = new ArrayList<>();
        for ( PointDetail pointDetail : pointDetailPage ) {
            getPointHistoryResList.add(GetPointHistoryRes.builder()
                    .point(pointDetail.getPoint())
                    .state(pointDetail.isState())
                    .description(pointDetail.getDescription())
                    .createdAt(pointDetail.getCreatedAt())
                    .build());
        }
        return getPointHistoryResList;
    }
}
