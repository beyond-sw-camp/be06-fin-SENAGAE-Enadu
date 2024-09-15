package org.example.backend.Wiki.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Category.Repository.CategoryRepository;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidCategoryException;
import org.example.backend.Exception.custom.InvalidUserException;
import org.example.backend.Exception.custom.InvalidWikiException;
import org.example.backend.Security.CustomUserDetails;
import org.example.backend.User.Model.Entity.User;
import org.example.backend.User.Repository.UserRepository;
import org.example.backend.Wiki.Model.Entity.LatestWiki;
import org.example.backend.Wiki.Model.Entity.Wiki;
import org.example.backend.Wiki.Model.Entity.WikiContent;
import org.example.backend.Wiki.Model.Req.GetWikiDetailReq;
import org.example.backend.Wiki.Model.Req.GetWikiListReq;
import org.example.backend.Wiki.Model.Req.GetWikiUpdateReq;
import org.example.backend.Wiki.Model.Req.WikiRegisterReq;
import org.example.backend.Wiki.Model.Res.GetWikiDetailRes;
import org.example.backend.Wiki.Model.Res.GetWikiUpdateRes;
import org.example.backend.Wiki.Model.Res.WikiListRes;
import org.example.backend.Wiki.Model.Res.WikiRegisterRes;
import org.example.backend.Wiki.Repository.LatestWikiRepository;
import org.example.backend.Wiki.Repository.WikiContentRepository;
import org.example.backend.Wiki.Repository.WikiRepository;
import org.example.backend.Wiki.Repository.WikiScrapRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class WikiService {
    private final WikiRepository wikiRepository;
    private final CategoryRepository categoryRepository;
    private final WikiContentRepository wikiContentRepository;
    private final LatestWikiRepository latestWikiRepository;
    private final UserRepository userRepository;
    private final WikiScrapRepository wikiScrapRepository;

    public WikiRegisterRes register(WikiRegisterReq wikiRegisterReq, String thumbnail, CustomUserDetails customUserDetails
    ) {
        // 위키 등록시 중복 확인 로직
        if (wikiRepository.findByTitle(wikiRegisterReq.getTitle()).isPresent()) {
            throw new InvalidWikiException(BaseResponseStatus.WIKI_CONTENT_DUPLICATION_FAIL);
        }
        // 유저 정보 확인 로직
        Optional<User> user = userRepository.findById(customUserDetails.getUserId());
        if (user.isEmpty()) {
            throw new InvalidUserException(BaseResponseStatus.USER_NOT_FOUND);
        }
        // Wiki 등록
        Wiki registerWiki = Wiki.builder()
                .category(categoryRepository.findById(wikiRegisterReq.getCategoryId()).orElseThrow(() -> new InvalidCategoryException(BaseResponseStatus.CATEGORY_NOT_FOUND_CATEGORY)))
                .title(wikiRegisterReq.getTitle())
                .build();
        wikiRepository.save(registerWiki);

        // Wiki Content 등록
        WikiContent registerContent = WikiContent.builder()
                .wiki(registerWiki)
                .user(user.get())
                .content(wikiRegisterReq.getContent())
                .version(1) //최초 버전
                .thumbnail(thumbnail)
                .build();
        wikiContentRepository.save(registerContent);

        // Latest Wiki 등록
        LatestWiki registerLatestWiki = LatestWiki.builder()
                .content(registerContent.getContent())
                .version(registerContent.getVersion())
                .thumbnailImgUrl(registerContent.getThumbnail())
                .build();
        latestWikiRepository.save(registerLatestWiki);

        registerWiki.updateLatestWiki(registerLatestWiki);
        wikiRepository.save(registerWiki);

        return WikiRegisterRes.builder().wikiId(registerWiki.getId()).build();
    }

    // 위키 목록 조회
    public List<WikiListRes> wikiList(GetWikiListReq getWikiListReq) {
        Pageable pageable = PageRequest.of(getWikiListReq.getPage(), getWikiListReq.getSize(), Sort.by(Sort.Direction.DESC, "latestWiki.createdAt"));
        Page<Wiki> wikiPage = wikiRepository.findAll(pageable);

        return wikiPage.getContent().stream().map
                        (wiki -> WikiListRes.builder()
                                .id(wiki.getId())
                                .title(wiki.getTitle())
                                .category(wiki.getCategory().getCategoryName())
                                .content(wiki.getLatestWiki().getContent())
                                .thumbnail(wiki.getLatestWiki().getThumbnailImgUrl())
                                .createdAt(wiki.getLatestWiki().getCreatedAt())
                                .build())
                .collect(Collectors.toList());
    }

    // 위키 상세 조회
    public GetWikiDetailRes detail(GetWikiDetailReq getWikiDetailReq, Long userId) {

        Wiki wiki = wikiRepository.findById(getWikiDetailReq.getId()).orElseThrow(() -> new InvalidWikiException(BaseResponseStatus.WIKI_NOT_FOUND_DETAIL));
        LatestWiki latestWiki = wiki.getLatestWiki(); //최신 위키

        GetWikiDetailRes wikiDetailRes = GetWikiDetailRes.builder()
                .id(wiki.getId())
                .title(wiki.getTitle())
                .content(wiki.getLatestWiki().getContent())
                .category(wiki.getCategory().getCategoryName())
                .version(latestWiki.getVersion())
                .checkScrap(ischeckScrap(wiki.getId(), latestWiki.getVersion(), userId))
                .build();
        return wikiDetailRes;

    }

    // 스크랩 여부 조회 메서드
    private Boolean ischeckScrap(Long wikiId, Integer version, Long userId) {

        if (userId == null) {
            return false;
        }
        WikiContent wikiContent = wikiContentRepository.findByWikiIdAndVersion(wikiId, version).orElseThrow(() ->
                new InvalidWikiException(BaseResponseStatus.WIKI_NOT_FOUND_DETAIL));
        return wikiScrapRepository.findByUserIdAndWikiContentId(userId, wikiContent.getId()).isPresent();

    }

    // 위키 수정
    public GetWikiUpdateRes update(GetWikiUpdateReq getWikiUpdateReq, String thumbnailUrl, Long userId) {

        User user = userRepository.findById(userId).get();

        if (user.getGrade().equals("뉴비")) {
            throw new InvalidWikiException(BaseResponseStatus.WIKI_PERMISSION_DENIED);
        }

        Wiki wiki = wikiRepository.findById(getWikiUpdateReq.getId()).orElseThrow(() -> new InvalidWikiException(BaseResponseStatus.WIKI_NOT_FOUND_DETAIL));
        LatestWiki latestWiki = wiki.getLatestWiki();

        // 썸네일 처리 로직
        String updateThumbnail = thumbnailUrl;
        if (updateThumbnail == null) {
            updateThumbnail = latestWiki.getThumbnailImgUrl();
            }
        // WikiContent 등록
        WikiContent updateWikiContent = WikiContent.builder()
                .wiki(wiki)
                .content(getWikiUpdateReq.getContent())
                .version(latestWiki.getVersion()+1)
                .user(user)
                .thumbnail(updateThumbnail)
                .build();
        wikiContentRepository.save(updateWikiContent);

        // LatestWiki 업데이트
        latestWiki.setContent(updateWikiContent.getContent());
        latestWiki.setVersion(updateWikiContent.getVersion());
        latestWiki.setThumbnailImgUrl(updateThumbnail);
        latestWikiRepository.save(latestWiki);

        return GetWikiUpdateRes.builder().wikiId(wiki.getId()).build();

    }
}




