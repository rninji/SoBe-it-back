package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.dto.*;
import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.GoalAmount;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.service.ProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile")
public class ProfileController {

    private final ProfileService profileService;

    /**
     * 프로필 유저 정보 가져오기
     * : 로그인된 사용자, 다른 사용자 프로필 조회
     * @param loggedInUser
     * @param userIdMap
     * @return 프로필에 표시되는 사용자 정보
     * */
    @PostMapping("/profileinfo")
    public ResponseEntity<?> profileinfo(@AuthenticationPrincipal Users loggedInUser, @RequestBody Map<String, String> userIdMap) {
        try {
            ProfileUserDTO profileUserDTO = profileService.selectUserInfo(loggedInUser.getUserId(), userIdMap.get("targetUserId"));
            return ResponseEntity.ok().body(profileUserDTO);
        } catch(Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }
    }

    /**
     * 작성한 글 목록 가져오기
     * : 로그인된 사용자 또는 다른 사용자가 작성한 글 목록 가져오기.
     *   추후 공개 여부에 따라 param을 @Authentication Users user, Users targetUser로 변경
     * @param userIdMap
     * @return 작성한 글 목록
     * */
    @RequestMapping("/myarticle")
    public ResponseEntity<?> articleList(@RequestBody Map<String, String> userIdMap) {
        try {
            System.out.println("articleList");
            List<Article> list = profileService.selectArticles(userIdMap.get("userId"));
            return ResponseEntity.ok().body(list);
        } catch(Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }
    }

    /**
     * 유저 프로필 편집 저장
     * -- parameter dto로 변경
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
            System.out.println("user = "+user);
            Users updatedUser = profileService.insertProfile(loggedInUser, user);
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
     * @param userIdMap
     * @return 사용자의 팔로잉 목록
     * */
    @RequestMapping("/following")
    public ResponseEntity<?> following(@AuthenticationPrincipal Users loggedInUser, @RequestBody Map<String, String> userIdMap) {
        try {
            List<ProfileDTO> list = profileService.selectFollowing(loggedInUser, userIdMap.get("userId"));
            return ResponseEntity.ok().body(list);
        } catch(Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }
    }

    /**
     * 팔로워 정보 가져오기
     * @param userIdMap
     * @return 사용자의 팔로워 목록
     * */
    @RequestMapping("/follower")
    public ResponseEntity<?> follower(@AuthenticationPrincipal Users loggedInUser, @RequestBody Map<String, String> userIdMap) {
        try {
            List<ProfileDTO > list = profileService.selectFollower(loggedInUser, userIdMap.get("userId"));
            return ResponseEntity.ok().body(list);
        } catch(Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }
    }

    /**
     * 팔로잉 해제
     * @param user
     * @param targetUserIdMap
     * @return 성공 시 "success", 실패 시 Error message
     * */
    @RequestMapping("/deleteFollowing")
    public ResponseEntity<?> deleteFollowing(@AuthenticationPrincipal Users user, @RequestBody Map<String, String> targetUserIdMap) {
        try {
            profileService.unfollow(user, targetUserIdMap.get("userId"));
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
     * @param targetUserIdMap
     * @return 성공 시 "success", 실패 시 Error message
     * */
    @RequestMapping("/addFollow")
    public ResponseEntity<?> addFollow(@AuthenticationPrincipal Users user, @RequestBody Map<String, String> targetUserIdMap) {
        try {
            profileService.follow(user, targetUserIdMap.get("userId"));
            return ResponseEntity.ok().body("success");
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }
    }
}