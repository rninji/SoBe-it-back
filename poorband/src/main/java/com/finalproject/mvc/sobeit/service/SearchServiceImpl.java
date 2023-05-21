package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.ArticleResponseDTO;
import com.finalproject.mvc.sobeit.dto.ProfileDTO;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.repository.ArticleRepo;
import com.finalproject.mvc.sobeit.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class SearchServiceImpl implements SearchService{

    private final UserRepo userRep;
    private final ArticleRepo articleRep;
    private final ArticleServiceImpl articleService;
    private final ProfileServiceImpl profileService;

    /**
     * 사용자(users) 검색
     **/
    @Override
    public List<ProfileDTO> usersSearch(@AuthenticationPrincipal Users loggedInUser, String userId, String inputText) {
        List<Long> searchUserSeqList = userRep.findAllByUserId(inputText);
        List<ProfileDTO> searchUserList = new ArrayList<>();
        if (searchUserSeqList == null || searchUserSeqList.size() == 0){
            throw new RuntimeException("검색어가 포함된 유저가 존재하지 않습니다.");
        }

        searchUserSeqList.forEach(u -> searchUserList.add(profileService.selectFollowingUser(loggedInUser, userId, u)));

        return searchUserList;
    }

    /**
     * 게시글(Articles) 검색
     **/
    @Override
    public List<ArticleResponseDTO> articlesSearch(Long userSeq, String inputText) {
        List<Long> searchArticleSeqList = articleRep.findArticlesByArticleText(inputText);
        List<ArticleResponseDTO> searchArticleList = new ArrayList<>();
        if (searchArticleSeqList == null || searchArticleSeqList.size() == 0){
            throw new RuntimeException("검색어가 포함된 게시글이 존재하지 않습니다.");
        }
        for (Long searchArticleSeq: searchArticleSeqList){
            ArticleResponseDTO dto = articleService.findArticleResponse(userSeq, searchArticleSeq);
            searchArticleList.add(dto);
        }
        return searchArticleList;
    }

}
