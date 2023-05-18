package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.dto.*;
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
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    /**
     * 프로필 유저 정보 가져오기
     * */
    @RequestMapping("/profileinfo")
    public ProfileUserDTO profileinfo(@AuthenticationPrincipal Users user) {
        ProfileUserDTO profileUserDTO = profileService.selectUserInfo(user);

        return profileUserDTO;
    }

    /**
     * 작성한 글 목록 가져오기
     * */
    @RequestMapping("/myarticle")
    public List<Article> articleList(@AuthenticationPrincipal Users user) {
        List<Article> list = profileService.selectMyArticle(user);

        return list;
    }

    /**
     * 도전 과제 정보 가져오기
     * */
    @RequestMapping("/challenge")
    public List<GoalAmount> challenge(@AuthenticationPrincipal Users user) {
        List<GoalAmount> list = profileService.selectChallenge(user);

        return list;
    }

    /**
     * 유저 프로필 편집 저장
     * */
    @RequestMapping("/save")
    public ResponseEntity<Object> save(@AuthenticationPrincipal Users loggedInUser, @RequestBody Users profile) {
        try{
            Users user = Users.builder()
                    .userId(profile.getUserId())
                    .nickname(profile.getNickname())
                    .introduction(profile.getIntroduction()) // 추후 이미지 편집도 추가?
                    .build();
            Users updatedUser = profileService.insertProfile(loggedInUser.getUserId(), user);
            if (updatedUser==null) {
                throw new RuntimeException("프로필 수정 실패");
            }

            return ResponseEntity.ok().body("success");
        } catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }
    }

    /**
     * 팔로잉 정보 가져오기
     * */
    @RequestMapping("/following")
    public List<Users> following(@AuthenticationPrincipal Users user) {
        List<Users> list = profileService.selectFollowing(user);

        return list;
    }

    /**
     * 팔로워 정보 가져오기
     * */
    @RequestMapping("/follower")
    public List<Users> follower(@AuthenticationPrincipal Users user) {
        List<Users> list = profileService.selectFollower(user);

        return list;
    }

    /**
     * 팔로잉 해제
     * */
    @RequestMapping("/deleteFollowing")
    public ResponseEntity<?> deleteFollowing(@AuthenticationPrincipal Users user, Users targetUser) throws Exception {
        try {
            profileService.unfollow(user, targetUser);
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }
    }

    /**
     * 팔로우 추가
     * */
    @RequestMapping("/addFollow")
    public ResponseEntity<?> addFollow(@AuthenticationPrincipal Users user, Users targetUser) {
        try {
            profileService.follow(user, targetUser);
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }
    }

    /**
     * 도전과제 추가
     * */
    @RequestMapping("/challenge/add")
    public void addChallenge(GoalAmount goalAmount) {
    }

    /**
     * 도전과제 삭제
     * */
    @RequestMapping("/challenge/delete")
    public void deleteChallenge(String goalAmountSeq) {

    }
}