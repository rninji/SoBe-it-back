package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.Article;
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

    /**
     * 사용자(users) 검색
     **/
    @Override
    public List<Users> usersSearch(String inputText) {
        List<Users> userSearchResult;
        try{
            userSearchResult = userRep.findAllByUserId(inputText);
        } catch (NullPointerException exception) { // 검색어가 포함되는 userId가 없을 경우(null)
            throw new RuntimeException("검색어와 일치하는 유저가 없습니다.");
        }
        return userSearchResult;
    }

    /**
     * 게시글(Articles) 검색
     **/
    @Override
    public List<Article>  articleSearch(String inputText) {

        return null;
    }
}
