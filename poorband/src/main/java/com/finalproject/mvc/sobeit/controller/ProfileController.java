package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.dto.ArticleDTO;
import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.Following;
import com.finalproject.mvc.sobeit.entity.GoalAmount;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.responses.ApiResponse;
import com.finalproject.mvc.sobeit.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;
    private final ApiResponse apiResponse;


    /**
     * 프로필 유저 정보 가져오기
     * */
    @RequestMapping("/profileinfo")
    public ResponseEntity<Object> profileinfo(String userId) {

        Users user = profileService.selectUserInfo(userId);
        apiResponse.setData(user);
        apiResponse.setMessage("프로필 유저 정보 가져오기");
        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);
    }

    /**
     * 작성한 글 가져오기
     * */
    @RequestMapping("/myarticle")
    public ResponseEntity<Object> articleList(String userId) {
//        List<ArticleDTO> list = profileService.selectMyArticle(userId);
        ArticleDTO articleDTO = profileService.selectMyArticle(userId);

        // list 가져오기

        JSONObject jsonObject = new JSONObject();
        jsonObject.put("userId", userId);
        return ResponseEntity.ok().body(jsonObject);

        apiResponse.setData(articleDTO);
        apiResponse.setMessage("작성한 글 가져오기");
        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);
    }

    /**
     * 도전 과제 정보 가져오기
     * */
    @RequestMapping("/challenge")
    public ResponseEntity<Object> challenge(String userId) {
        List<GoalAmount> list = profileService.selectChallenge(userId);
        apiResponse.setData(list);
        apiResponse.setMessage("도전 과제 정보 가져오기");
        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);

    }

    /**
     * 유저 프로필 편집 저장
     * */
    @RequestMapping("/save")
    public ResponseEntity<Object> save(Users user) {
        profileService.insertProfile(user);
        apiResponse.setData(user);
        apiResponse.setMessage("유저 프로필 편집 저장");
        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);

    }

    /**
     * 팔로잉 정보 가져오기
     * */
    @RequestMapping("/following")
    public ResponseEntity<Object> following(String userId) {
        Users user = profileService.selectUserInfo(userId);
        apiResponse.setData(user);
        apiResponse.setMessage("프로필 유저 정보 가져오기");
        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);

    }

    /**
     * 팔로워 정보 가져오기
     * */
    @RequestMapping("/follower")
    public ResponseEntity<Object> follower(String userId) {
        Users user = profileService.selectUserInfo(userId);
        apiResponse.setData(user);
        apiResponse.setMessage("프로필 유저 정보 가져오기");
        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);

    }

    /**
     * 팔로잉 해제
     * */
    @RequestMapping("/deleteFollowing")
    public ResponseEntity<Object> deleteFollowing(String userId, String target_user_id) {
        Users user = profileService.selectUserInfo(userId);
        apiResponse.setData(user);
        apiResponse.setMessage("프로필 유저 정보 가져오기");
        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);

    }

    /**
     * 팔로우 추가
     * */
    @RequestMapping("/addFollow")
    public ResponseEntity<Object> addFollow(String userId) {
        Users user = profileService.selectUserInfo(userId);
        apiResponse.setData(user);
        apiResponse.setMessage("프로필 유저 정보 가져오기");
        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);

    }

    /**
     * 도전과제 추가
     * */
    @RequestMapping("/challenge/add")
    public ResponseEntity<Object> addChallenge(GoalAmount goalAmount) {
//        Users user = profileService.selectUserInfo(goalAmount);
//        apiResponse.setData(user);
//        apiResponse.setMessage("프로필 유저 정보 가져오기");
        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);

    }

    /**
     * 도전과제 삭제
     * */
    @RequestMapping("/challenge/delete")
    public ResponseEntity<Object> deleteChallenge(String goalAmountSeq) {
        Users user = profileService.selectUserInfo(goalAmountSeq);
        apiResponse.setData(user);
        apiResponse.setMessage("프로필 유저 정보 가져오기");
        return new ResponseEntity<>(apiResponse.getBodyResponse(), HttpStatus.OK);

    }

}