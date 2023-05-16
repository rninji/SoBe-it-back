package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.repository.ArticleRepo;
import com.finalproject.mvc.sobeit.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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

        return userRep.findAllByUserId(inputText);
    }

    /**
     * 게시글(Articles) 검색
     **/
    @Override
    public List<Article> articleSearch(String inputText) {

        return null;
    }
}
