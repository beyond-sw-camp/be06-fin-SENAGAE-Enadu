package org.example.backend.Main.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.ErrorArchive.Model.Entity.ErrorArchive;
import org.example.backend.ErrorArchive.Model.Res.ListErrorArchiveRes;
import org.example.backend.ErrorArchive.Repository.ErrorArchiveReository;
import org.example.backend.Main.Model.Res.MainRes;
import org.example.backend.Qna.Repository.QuestionRepository;
import org.example.backend.Qna.model.Entity.QnaBoard;
import org.example.backend.Qna.model.Res.GetQnaListRes;
import org.example.backend.Wiki.Model.Entity.Wiki;
import org.example.backend.Wiki.Model.Res.WikiListRes;
import org.example.backend.Wiki.Repository.WikiRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MainService {
    private final ErrorArchiveReository errorArchiveReository;
    private final WikiRepository wikiRepository;
    private final QuestionRepository questionRepository;

    public MainRes getMainInfo(Integer errorArchiveSize, Integer wikiSize, Integer qnaSize) {
        Pageable errorArchivePageable = PageRequest.of(0, errorArchiveSize, Sort.by(Sort.Direction.DESC, "createdAt"));
        Pageable wikiPageable = PageRequest.of(0, wikiSize, Sort.by(Sort.Direction.DESC, "latestWiki.createdAt"));
        Pageable qnaPageable = PageRequest.of(0, qnaSize, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<ErrorArchive> errorArchivePage = errorArchiveReository.findByEnableTrue(errorArchivePageable);
        Page<Wiki> wikiPage = wikiRepository.findAll(wikiPageable);
        Page<QnaBoard> qnaBoardPage = questionRepository.findByEnableTrue(qnaPageable);

        List<ListErrorArchiveRes> listErrorArchiveResList = errorArchivePage.getContent().stream().map
                        (errorArchive -> ListErrorArchiveRes.builder()
                                .id(errorArchive.getId())
                                .title(errorArchive.getTitle())
                                .content(errorArchive.getContent())
                                .superCategory(errorArchive.getCategory().getSuperCategory() == null ?
                                        errorArchive.getCategory().getCategoryName() :
                                        errorArchive.getCategory().getSuperCategory().getCategoryName())
                                .subCategory(errorArchive.getCategory().getSuperCategory() == null ? null : errorArchive.getCategory().getCategoryName())
                                .likeCnt(errorArchive.getLikeCount())
                                .createdAt(String.valueOf(errorArchive.getCreatedAt()))
                                .grade(errorArchive.getUser().getGrade())
                                .nickname(errorArchive.getUser().getNickname())
                                .profileImg(errorArchive.getUser().getProfileImg())
                                .totalPage(errorArchivePage.getTotalPages())
                                .build())
                .collect(Collectors.toList());

        List<WikiListRes> listWikiResList = wikiPage.getContent().stream().map
                        (wiki -> WikiListRes.builder()
                                .id(wiki.getId())
                                .title(wiki.getTitle())
                                .category(wiki.getCategory().getCategoryName())
                                .content(wiki.getLatestWiki().getContent())
                                .thumbnail(wiki.getLatestWiki().getThumbnailImgUrl())
                                .createdAt(wiki.getLatestWiki().getCreatedAt())
                                .totalPages(wikiPage.getTotalPages())
                                .build())
                .collect(Collectors.toList());

        List<GetQnaListRes> listQnaResList = qnaBoardPage.getContent().stream().map
                        (qnaBoard -> GetQnaListRes.builder()
                                .id(qnaBoard.getId())
                                .title(qnaBoard.getTitle())
                                .superCategoryName(qnaBoard.getCategory().getSuperCategory() != null ?
                                        qnaBoard.getCategory().getSuperCategory().getCategoryName() : null)
                                .subCategoryName(qnaBoard.getCategory() != null ?
                                        qnaBoard.getCategory().getCategoryName() : null)
                                .subCategoryName(qnaBoard.getCategory().getCategoryName())
                                .nickname(qnaBoard.getUser().getNickname())
                                .profileImage(qnaBoard.getUser().getProfileImg())
                                .grade(qnaBoard.getUser().getGrade())
                                .likeCnt(qnaBoard.getLikeCount())
                                .answerCnt(qnaBoard.getAnswerCount())
                                .createdAt(qnaBoard.getCreatedAt())
                                .build())
                .collect(Collectors.toList());

        return MainRes.builder()
                .qnaListResList(listQnaResList)
                .wikiListResList(listWikiResList)
                .errorArchiveListResList(listErrorArchiveResList)
                .build();
    }
}
