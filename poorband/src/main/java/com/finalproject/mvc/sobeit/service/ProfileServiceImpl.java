package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.FollowDTO;
import com.finalproject.mvc.sobeit.entity.*;
import com.finalproject.mvc.sobeit.repository.ArticleRepo;
import com.finalproject.mvc.sobeit.repository.FollowingRepo;
import com.finalproject.mvc.sobeit.repository.GoalAmountRepo;
import com.finalproject.mvc.sobeit.repository.UserRepo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileServiceImpl implements ProfileService {

    private UserRepo userRepo;
    private ArticleRepo articleRepo;
    private GoalAmountRepo goalAmountRepo;
    private FollowingRepo followingRepo;
    private final JPAQueryFactory queryFactory;

    @Override
    public Users selectUserInfo(String user_id) {
        Users user = new Users();

        user.setProfileImageUrl(userRepo.findByUserId(user_id).getProfileImageUrl());
        user.setNickname(userRepo.findByUserId(user_id).getNickname());
        user.setUserId(user_id);
        user.setIntroduction(userRepo.findByUserId(user_id).getIntroduction());


        // followingCnt
        // followerCnt


        return user;
    }

    @Override
    public List<Article> selectMyArticle(String user_id) {
        List<Article> userArticles = articleRepo.findArticlesByUser(user_id);

        return userArticles;
    }

    @Override
    public List<GoalAmount> selectChallenge(String user_id) {
        List<GoalAmount> goalAmountList = goalAmountRepo.findGoalAmountByUserId(user_id);

        return goalAmountList;
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
    public void unfollow(Long userSeq, boolean state) throws Exception {
        Users followingUser = userRepo.findByUserSeq(userSeq); //.orElse(null);
        if(followingUser == null) {
            throw new Exception("User not found");
        }
        FollowDTO f = new FollowDTO();
        f.setFollower(followingUser);
        f.setFollowing(userRepo.findByUserSeq(userSeq));
//        followingRepo.save(f);
    }

    @Override
    public void follow(Long userSeq, boolean state) throws Exception {

        Users followingUser = userRepo.findByUserSeq(userSeq); //.orElse(null);
        if(followingUser == null) {
            throw new Exception("User not found");
        }
        FollowDTO f = new FollowDTO();
        f.setFollower(followingUser);
        f.setFollowing(userRepo.findByUserSeq(userSeq));
//        followingRepo.save(f);
    }

    @Override
    public void insertChallenge(GoalAmount challenge) {

    }

    @Override
    public void deleteChallenge(Long userSeq, Long challenge_seq) {

    }

}