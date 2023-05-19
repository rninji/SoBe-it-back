package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.ArticleResponseDTO;
import com.finalproject.mvc.sobeit.dto.ProfileUserDTO;
import com.finalproject.mvc.sobeit.entity.*;
import com.finalproject.mvc.sobeit.repository.ArticleRepo;
import com.finalproject.mvc.sobeit.repository.FollowingRepo;
import com.finalproject.mvc.sobeit.repository.GoalAmountRepo;
import com.finalproject.mvc.sobeit.repository.UserRepo;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileServiceImpl implements ProfileService {

    private final UserRepo userRepo;
    private final ArticleRepo articleRepo;
    private final GoalAmountRepo goalAmountRepo;
    private final FollowingRepo followingRepo;
    private final JPAQueryFactory queryFactory;

    /**
     * 프로필 유저 정보 가져오기
     * */
    @Override
    public ProfileUserDTO selectUserInfo(Users loggedInUser) {
        Users user = userRepo.findByUserId(loggedInUser.getUserId());

        ProfileUserDTO profileUserDTO = new ProfileUserDTO();

        profileUserDTO.setProfileImg(user.getProfileImageUrl());
        profileUserDTO.setNickname(user.getNickname());
        profileUserDTO.setUserId(loggedInUser.getUserId());
        profileUserDTO.setIntroDetail(user.getIntroduction());
        profileUserDTO.setFollowingCnt(followingRepo.followingCnt(user.getUserSeq()));
        profileUserDTO.setFollowerCnt(followingRepo.followerCnt(user.getUserSeq()));

        return profileUserDTO;
    }

    /**
     * 작성한 글 가져오기
     * */
    @Override
    public List<Article> selectMyArticle(Users user) {
        List<Article> userArticles = articleRepo.findArticlesByUser(user.getUserId());

        return userArticles;
    }

    /**
     * 도전 과제 정보 가져오기
     * */
    @Override
    public List<GoalAmount> selectChallenge(Users user) {
//        List<GoalAmount> goalAmountList = goalAmountRepo.findGoalAmountByUserId(userId);

        return null;
    }

    /**
     * 유저 프로필 편집 저장
     *
     * @return*/
    @Override
    public Users insertProfile(String userId, Users updateUser) {
        Users user = userRepo.findByUserId(updateUser.getUserId());

        user.setNickname(updateUser.getNickname());
        user.setIntroduction(updateUser.getIntroduction());

        userRepo.save(user);
        return user;
    }

    /**
     * 팔로잉 / 팔로워 타이틀
     * */

    /**
     * 팔로잉 정보 가져오기
     * */
    @Override
    public List<Users> selectFollowing(Users user) {
        List<Users> followingList = followingRepo.findArticleThatUserFollows(user.getUserSeq());

        return followingList;
    }

    /**
     * 팔로워 정보 가져오기
     * */
    @Override
    public List<Users> selectFollower(Users user) {
        List<Users> followerList = followingRepo.findArticleThatUserFollowed(user.getUserSeq());

        return followerList;
    }

    /**
     * 팔로잉 해제
     * */
    @Override
    public Following unfollow(@AuthenticationPrincipal Users user, Users targetUser) throws Exception {
        Users followingUser = userRepo.findById(targetUser.getUserSeq()).orElse(null);

        // 팔로우하려는 사용자가 없음.
        if(followingUser == null) {
            throw new Exception("User not found");
        }

        Following f = followingRepo.findByFollowingAndFollower(user.getUserSeq(), targetUser.getUserSeq()).orElse(null);


        // 서로 팔로잉 관계가 아닐 때
        if(f == null) {
            throw new Exception("User not following " + targetUser.getNickname());
        }

        return followingRepo.save(f);
    }

    /**
     * 팔로우 추가
     *
     * @return*/
    @Override
    public Following follow(@AuthenticationPrincipal Users user, Users targetUser) throws Exception {

        Users loggedInUser = userRepo.findById(user.getUserSeq()).orElse(null);
        Users followingUser = userRepo.findById(targetUser.getUserSeq()).orElse(null);

        // 팔로우하려는 사용자가 없음.
        if(followingUser == null) {
            throw new Exception("User not found!");
        }

        Following f = new Following();
        f.setUser(user);
        f.setFollowingUserSeq(targetUser.getUserSeq());

        return followingRepo.save(f);
    }
}