package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.Following;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.service.ProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private ProfileService profileService;

    /**
     * 프로필 유저 정보 가져오기
     * */
    @RequestMapping("/profileinfo")
    public void profileinfo() {

        Users selectUserInfo(String user_id);

        Users user = profileService.selectUserInfo('kkk');

        List<Article> list = profileService.selectMyArticle("kkk");


    }

}