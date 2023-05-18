package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/search")
public class SearchController {

    private final SearchService searchService;

    /**
     * 사용자(users) 검색
     **/
    @RequestMapping("/users")
    public List<Users> usersSearch(String inputText){
        List<Users> usersList = searchService.usersSearch(inputText);

        return usersList;
    }

    /**
     * 게시글(Articles) 검색
     **/
    @RequestMapping("/articles")
    public List<Article> articlesSearch(String inputText){
        List<Article> articleList = searchService.articlesSearch(inputText);

        return articleList;
    }
}
