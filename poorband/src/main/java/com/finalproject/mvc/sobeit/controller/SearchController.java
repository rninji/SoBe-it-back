package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.service.SearchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/search")
public class SearchController {

    @Autowired
    private SearchService searchService;

    /**
     * 사용자(users) 검색
     **/
    @RequestMapping("/users")
    public String usersSearch(String inputText){
        searchService.usersSearch(inputText);

        return "redirect:/search/users";
    }

    /**
     * 게시글(Articles) 검색
     **/

}
