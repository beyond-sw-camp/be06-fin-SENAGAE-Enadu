package org.example.backend.Wiki.Service;

import lombok.RequiredArgsConstructor;
import org.example.backend.Category.Repository.CategoryRepository;
import org.example.backend.Exception.custom.InvalidCategoryException;
import org.example.backend.Exception.custom.InvalidWikiException;
import org.example.backend.Wiki.Model.Entity.LatestWiki;
import org.example.backend.Wiki.Model.Entity.Wiki;
import org.example.backend.Wiki.Model.Entity.WikiContent;
import org.example.backend.Wiki.Model.Req.WikiRegisterReq;
import org.example.backend.Wiki.Model.Res.WikiRegisterRes;
import org.example.backend.Wiki.Repository.LatestWikiRepository;
import org.example.backend.Wiki.Repository.WikiContentRepository;
import org.example.backend.Wiki.Repository.WikiRepository;
import org.springframework.stereotype.Service;

import static org.example.backend.Common.BaseResponseStatus.*;

@Service
@RequiredArgsConstructor
public class WikiService {
    private final WikiRepository wikiRepository;
    private final CategoryRepository categoryRepository;
    private final WikiContentRepository wikiContentRepository;
    private final LatestWikiRepository latestWikiRepository;

    // 위키 등록 ToDo : 매개변수 customUserDetails 유저정보 추가
    public WikiRegisterRes register(WikiRegisterReq wikiRegisterReq, String thumbnail) {
        if (wikiRepository.findByTitle(wikiRegisterReq.getTitle()).isPresent()) {
            throw new InvalidWikiException(WIKI_REGIST_FAIL);
        }
        // Wiki 등록
        Wiki registerWiki = Wiki.builder()
                .category(categoryRepository.findById(wikiRegisterReq.getCategoryId()).orElseThrow(() -> new InvalidCategoryException(CATEGORY_NOT_FOUND_CATEGORY)))
                .title(wikiRegisterReq.getTitle())
                .build();
        wikiRepository.save(registerWiki);

        // Wiki Content 등록
        WikiContent registerContent = WikiContent.builder()
                .wiki(registerWiki)
                //.user() ToDo : 위키 등록 작성자 정보
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
}
