package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.ArticleResponseDTO;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.repository.ArticleRepo;
import com.finalproject.mvc.sobeit.repository.UserRepo;
import lombok.RequiredArgsConstructor;
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
    private  final ArticleServiceImpl articleService;

    /**
     * 사용자(users) 검색
     **/
    @Override
    public List<Users> usersSearch(String inputText) {
        List<Users> userSearchResult;
        try{
            userSearchResult = userRep.findAllByUserId(inputText);
            System.out.println("userSearchResult = " + userSearchResult);
        } catch (NullPointerException exception) { // 검색어가 포함되는 userId가 없을 경우(null)
            throw new RuntimeException("검색어와 포함된 유저가 없습니다.");
        }
        return userSearchResult;
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
