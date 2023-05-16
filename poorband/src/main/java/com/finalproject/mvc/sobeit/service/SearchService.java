package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.Users;

import java.util.List;

public interface SearchService {
    /**
     * 사용자(users) 검색
     **/
    List<Users> usersSearch(String inputText);

    /**
     * 게시글(Articles) 검색
     **/
    List<Article> articleSearch(String inputText);

}
