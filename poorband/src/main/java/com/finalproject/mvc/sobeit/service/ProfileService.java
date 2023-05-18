package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.ArticleResponseDTO;
import com.finalproject.mvc.sobeit.dto.ProfileUserDTO;
import com.finalproject.mvc.sobeit.entity.*;

import java.util.List;

public interface ProfileService {

    /**
     * 프로필 유저 정보 가져오기
     * */
    ProfileUserDTO selectUserInfo(Users user);

    /**
     * 작성한 글 가져오기
     * */
    List<Article> selectMyArticle(Users user);

    /**
     * 도전 과제 정보 가져오기
     * */
    List<GoalAmount> selectChallenge(Users user);

    /**
     * 유저 프로필 편집 저장
     *
     * */
    Users insertProfile(String userId, Users updateUser);

    /**
     * 팔로잉 정보 가져오기
     * */
    List<Users> selectFollowing(Users user);

    /**
     * 팔로워 정보 가져오기
     * */
    List<Users> selectFollower(Users user);

    /**
     * 팔로잉 해제
     * */
    Following unfollow(Users user, Users targetUser) throws Exception;

    /**
     * 팔로우 추가
     * */
    Following follow(Users user, Users targetUser) throws Exception;

    /**
     * 도전과제 추가
     * */
    void insertChallenge(String userId, GoalAmount challenge);

    /**
     * 도전과제 삭제
     * */
    void deleteChallenge(String userId, Long challengeSeq);

}