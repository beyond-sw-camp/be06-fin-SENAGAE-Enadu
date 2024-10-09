package org.example.backend.Wiki.Service;

import lombok.RequiredArgsConstructor;
import com.example.common.Category.Repository.CategoryRepository;
import org.example.backend.Common.BaseResponseStatus;
import org.example.backend.Exception.custom.InvalidCategoryException;
import org.example.backend.Exception.custom.InvalidUserException;
import org.example.backend.Exception.custom.InvalidWikiException;
import org.example.backend.Security.CustomUserDetails;
import com.example.common.User.Model.Entity.User;
import com.example.common.User.Repository.UserRepository;
import com.example.common.Wiki.Model.Entity.LatestWiki;
import com.example.common.Wiki.Model.Entity.Wiki;
import com.example.common.Wiki.Model.Entity.WikiContent;
import com.example.common.Wiki.Model.Entity.WikiScrap;
import org.example.backend.Wiki.Model.Req.*;
import org.example.backend.Wiki.Model.Res.*;
import com.example.common.Wiki.Repository.LatestWikiRepository;
import com.example.common.Wiki.Repository.WikiContentRepository;
import com.example.common.Wiki.Repository.WikiRepository;
import com.example.common.Wiki.Repository.WikiScrapRepository;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
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

    @Transactional
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
        if(user.get().getGrade().equals("뉴비")) {
            throw new InvalidWikiException(BaseResponseStatus.WIKI_PERMISSION_DENIED);
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
    public List<WikiListRes> wikiList(Integer page,Integer size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "latestWiki.createdAt"));
        Page<Wiki> wikiPage = wikiRepository.findAll(pageable);

        return wikiPage.getContent().stream().map
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
    }

    // 위키 상세 조회
    public GetWikiDetailRes detail(Long id, Long userId) {

        String userGrade = "GUEST";

        if (userId != null) {
            Optional<User> userOptional = userRepository.findById(userId);
            if (userOptional.isPresent()) {
                userGrade = userOptional.get().getGrade();  // 유저 등급 설정
            }
        }

        Wiki wiki = wikiRepository.findById(id).orElseThrow(() -> new InvalidWikiException(BaseResponseStatus.WIKI_NOT_FOUND_DETAIL));
        LatestWiki latestWiki = wiki.getLatestWiki(); //최신 위키
        WikiContent wikiContent = wikiContentRepository.findByWikiIdAndVersion(wiki.getId(), latestWiki.getVersion()).get();

        GetWikiDetailRes wikiDetailRes = GetWikiDetailRes.builder()
                .id(wiki.getId())
                .title(wiki.getTitle())
                .content(wiki.getLatestWiki().getContent())
                .category(wiki.getCategory().getCategoryName())
                .version(latestWiki.getVersion())
                .checkScrap(ischeckScrap(wiki.getId(), latestWiki.getVersion(), userId))
                .userGrade(userGrade)
                .wikiContentId(wikiContent.getId())
                .build();
        return wikiDetailRes;

    }

    // 위키 수정
    @Transactional
    public GetWikiUpdateRes update(GetWikiUpdateReq getWikiUpdateReq, String thumbnailUrl, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidWikiException(BaseResponseStatus.WIKI_PERMISSION_DENIED));

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
                .version(latestWiki.getVersion() + 1)
                .user(user)
                .thumbnail(updateThumbnail)
                .build();
        wikiContentRepository.save(updateWikiContent);

        // LatestWiki 업데이트
        latestWiki.updateContentAndVersion(updateWikiContent.getContent(), updateWikiContent.getVersion());
        latestWiki.updateThumbnail(updateThumbnail);
        latestWikiRepository.save(latestWiki);

        return GetWikiUpdateRes.builder().wikiId(wiki.getId()).build();

    }

    // 위키 이전버전 상세 조회
    public GetWikiVersionDetailRes versionDetail(Long wikiContentId, Long userId){

        WikiContent wikiContent = wikiContentRepository.findById(wikiContentId).orElseThrow(() -> new InvalidWikiException(BaseResponseStatus.WIKI_NOT_FOUND_DETAIL));
        Wiki wiki = wikiContent.getWiki();
        GetWikiVersionDetailRes wikiDetailRes = GetWikiVersionDetailRes.builder()
                .id(wiki.getId())
                .wikiContentId(wikiContent.getId())
                .title(wiki.getTitle())
                .content(wikiContent.getContent())
                .category(wiki.getCategory().getCategoryName())
                .version(wikiContent.getVersion())
                .checkScrap(ischeckScrap(wiki.getId(), wikiContent.getVersion(), userId))
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
      
    // 위키 (이전버전) 목록 조회
    public List<GetWikiVersionListRes> versionList(GetWikiVersionListReq getWikiVersionListReq) {
        Pageable pageable = PageRequest.of(getWikiVersionListReq.getPage(), getWikiVersionListReq.getSize(), Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<WikiContent> wikiVersionPage = wikiContentRepository.findAllByWikiId(getWikiVersionListReq.getId(), pageable);


        return wikiVersionPage.getContent().stream().map
                        (wikiContent -> GetWikiVersionListRes.builder()
                                .wikiContentId(wikiContent.getId())
                                .version(wikiContent.getVersion())
                                .createdAt(wikiContent.getCreatedAt())
                                .nickname(wikiContent.getUser().getNickname())
                                .totalPages(wikiVersionPage.getTotalPages())
                                .build())
                .collect(Collectors.toList());
    }

    // 위키 스크랩
    @Transactional
    public WikiScrapRes scrap(WikiScrapReq wikiScrapReq, Long userId) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new InvalidUserException(BaseResponseStatus.USER_NOT_LOGIN));

        WikiContent wikiContent = wikiContentRepository.findById(wikiScrapReq.getWikiContentId())
                .orElseThrow(() -> new InvalidWikiException(BaseResponseStatus.WIKI_NOT_FOUND_DETAIL));

        Optional<WikiScrap> checkScrap = wikiScrapRepository.findByUserIdAndWikiContentId(userId, wikiContent.getId());

        if (checkScrap.isPresent()) {
            wikiScrapRepository.delete(checkScrap.get());
            return WikiScrapRes.builder().wikiContentId(wikiContent.getId()).build();

        } else {
            WikiScrap wikiScrap = WikiScrap.builder()
                    .user(user)
                    .wikiContent(wikiContent)
                    .build();
            wikiScrapRepository.save(wikiScrap);
        }
        return WikiScrapRes.builder().wikiContentId(wikiContent.getId()).build();
    }
      
    // 위키 롤백
    @Transactional
    public WikiRollbackRes rollback(WikiRollbackReq wikiRollbackReq, Long userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new InvalidWikiException(BaseResponseStatus.WIKI_PERMISSION_DENIED));
        if (user.getGrade().equals("뉴비")) {
            throw new InvalidWikiException(BaseResponseStatus.WIKI_PERMISSION_DENIED);
        }

        WikiContent wikiContent = wikiContentRepository.findById(wikiRollbackReq.getWikiContentId()).orElseThrow(() -> new InvalidWikiException(BaseResponseStatus.WIKI_NOT_FOUND_DETAIL));
        Wiki wiki = wikiContent.getWiki();
        LatestWiki latestWiki = wiki.getLatestWiki();

        WikiContent rollbackWikiContent = WikiContent.builder()
                .wiki(wiki)
                .content(wikiContent.getContent())
                .version(latestWiki.getVersion()+1)
                .user(wikiContent.getUser())
                .thumbnail(wikiContent.getThumbnail())
                .build();
        wikiContentRepository.save(rollbackWikiContent);

        // LatestWiki 업데이트
        latestWiki.updateContentAndVersion(rollbackWikiContent.getContent(), rollbackWikiContent.getVersion());
        latestWiki.updateThumbnail(rollbackWikiContent.getThumbnail());
        latestWikiRepository.save(latestWiki);

        return WikiRollbackRes.builder().id(wiki.getId()).build();
    }
}


