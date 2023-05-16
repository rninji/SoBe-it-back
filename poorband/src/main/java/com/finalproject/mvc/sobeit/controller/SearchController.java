package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.service.SearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        List<Users> list = searchService.usersSearch(inputText);

        return list;
    }

    /**
     * 게시글(Articles) 검색
     **/

}
