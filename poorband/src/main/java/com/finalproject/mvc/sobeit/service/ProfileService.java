package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.ArticleDTO;
import com.finalproject.mvc.sobeit.entity.*;

import java.util.List;

public interface ProfileService {

    /**
     * 프로필 유저 정보 가져오기
     * */
    Users selectUserInfo(String userId);

    /**
     * 작성한 글 가져오기
     * */
    ArticleDTO selectMyArticle(String userId);

    /**
     * 도전 과제 정보 가져오기
     * */
    List<GoalAmount> selectChallenge(String userId);

    /**
     * 유저 프로필 편집 저장
     * */
    void insertProfile(Users users);

    /**
     * 팔로잉 정보 가져오기
     * */
    List<Following> selectFollowing();

    /**
     * 팔로워 정보 가져오기
     * */
    List<Following> selectFollower();

    /**
     * 팔로잉 해제
     * */
    void unfollow(Long userSeq, boolean state) throws Exception;

    /**
     * 팔로우 추가
     * */
    void follow(Long userSeq, boolean state) throws Exception;

    /**
     * 도전과제 추가
     * */
    void insertChallenge(String userId, GoalAmount challenge);

    /**
     * 도전과제 삭제
     * */
    void deleteChallenge(String userId, Long challengeSeq);

}