package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.*;
import com.finalproject.mvc.sobeit.repository.ArticleRepo;
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

    private UserRepo userRepo;
    private ArticleRepo articleRepo;
    private GoalAmount goalAmountRepo;
    private final JPAQueryFactory queryFactory;

    @Override
    public Users selectUserInfo(String user_id) {
        Users user = new Users();

        user.setProfile_image_url(userRepo.findByUser_id(user_id).getUser_id());
        user.setNickname(userRepo.findByUser_id(user_id).getNickname());
        user.setUser_id(user_id);
        user.setIntroduction(userRepo.findByUser_id(user_id).getIntroduction());


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
//        List<GoalAmount> goalAmountList = goalAmountRepo.findGoalAmountByUser(user_id);

//        return goalAmountList;
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