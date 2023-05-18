package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.dto.*;
import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.GoalAmount;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    /**
     * 프로필 유저 정보 가져오기
     * : 로그인된 사용자, 다른 사용자 프로필 조회
     * @param user
     * @return 프로필에 표시되는 사용자 정보
     * */
    @RequestMapping("/profileinfo")
    public ProfileUserDTO profileinfo(Users user) {
        ProfileUserDTO profileUserDTO = profileService.selectUserInfo(user);

        return profileUserDTO;
    }

    /**
     * 작성한 글 목록 가져오기
     * : 로그인된 사용자 또는 다른 사용자가 작성한 글 목록 가져오기.
     *   추후 공개 여부에 따라 param을 @Authentication Users user, Users targetUser로 변경
     * @param user
     * @return 작성한 글 목록
     * */
    @RequestMapping("/myarticle")
    public List<Article> articleList(Users user) {
        List<Article> list = profileService.selectMyArticle(user);

        return list;
    }

    /**
     * 도전 과제 정보 가져오기
     * @param user
     * @return 도전 과제 목록
     * */
    @RequestMapping("/challenge")
    public List<GoalAmount> challenge(Users user) {
        List<GoalAmount> list = profileService.selectChallenge(user);

        return list;
    }

    /**
     * 유저 프로필 편집 저장
     * @param loggedInUser
     * @param profile
     * @return 성공 시 "success", 실패 시 Error message
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
     * @param user
     * @return 사용자의 팔로잉 목록
     * */
    @RequestMapping("/following")
    public List<Users> following(Users user) {
        List<Users> list = profileService.selectFollowing(user);

        return list;
    }

    /**
     * 팔로워 정보 가져오기
     * @param user
     * @return 사용자의 팔로워 목록
     * */
    @RequestMapping("/follower")
    public List<Users> follower(Users user) {
        List<Users> list = profileService.selectFollower(user);

        return list;
    }

    /**
     * 팔로잉 해제
     * @param user
     * @param targetUser
     * @return 성공 시 "success", 실패 시 Error message
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
     * @param user
     * @param targetUser
     * @return 성공 시 "success", 실패 시 Error message
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