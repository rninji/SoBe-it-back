package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.*;
import com.finalproject.mvc.sobeit.repository.UserRepo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileServiceImpl implements ProfileService {

    private UserRepo userRep;
    private final JPAQueryFactory queryFactory;

    @Override
    public Users selectUserInfo(String user_id) {
        Users user = new Users();

        user.setProfile_image_url(userRep.findByUserId(user_id).getUser_id());
        user.setNickname(userRep.findByUserId(user_id).getNickname());
        user.setUser_id(user_id);
        user.setIntroduction(userRep.findByUserId(user_id).getIntroduction());


        // followingCnt
        // followerCnt


        return user;
    }

    @Override
    public List<Article> selectMyArticle(String user_id) {
        List<Article> userArticles;
        return null;
    }

    @Override
    public List<GoalAmount> selectChallenge() {
        return null;
    }

    @Override
    public void insertProfile(Users users) {

    }

    @Override
    public List<Following> selectFollowing() {
        return null;
    }

    @Override
    public List<Following> selectFollower() {
        return null;
    }

    @Override
    public void unfollow(Long userSeq, boolean state) {

    }

    @Override
    public void follow(Long userSeq, boolean state) {

    }

    @Override
    public void insertChallenge(GoalAmount challenge) {

    }

    @Override
    public void deleteChallenge(Long userSeq, Long challenge_seq) {

    }

}