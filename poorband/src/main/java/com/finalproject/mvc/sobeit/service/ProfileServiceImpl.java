package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.ArticleResponseDTO;
import com.finalproject.mvc.sobeit.dto.ProfileDTO;
import com.finalproject.mvc.sobeit.dto.ProfileUserDTO;
import com.finalproject.mvc.sobeit.entity.*;
import com.finalproject.mvc.sobeit.repository.ArticleRepo;
import com.finalproject.mvc.sobeit.repository.FollowNotificationRepo;
import com.finalproject.mvc.sobeit.repository.FollowingRepo;
import com.finalproject.mvc.sobeit.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ProfileServiceImpl implements ProfileService {

    private final UserRepo userRepo;
    private final ArticleRepo articleRepo;
    private final FollowingRepo followingRepo;
    private final FollowNotificationRepo followNotificationRepo;

    /**
     * 프로필 유저 정보 가져오기
     * */
    @Override
    public ProfileUserDTO selectUserInfo(String userId) throws Exception {
        Users user = userRepo.findByUserId(userId);

        if(user == null) throw new RuntimeException("사용자 정보가 없습니다.");

        ProfileUserDTO profileUserDTO = new ProfileUserDTO();

        profileUserDTO.setProfileImg(user.getProfileImageUrl());
        profileUserDTO.setNickname(user.getNickname());
        profileUserDTO.setUserId(user.getUserId());
        profileUserDTO.setIntroDetail(user.getIntroduction());
        profileUserDTO.setFollowingCnt(followingRepo.followingCnt(user));
        profileUserDTO.setFollowerCnt(followingRepo.followerCnt(user));

        return profileUserDTO;
    }

    /**
     * 작성한 글 가져오기
     * */
    @Override

    public List<Article> selectArticles(String userId) throws Exception {
        List<Article> userArticles = articleRepo.findArticlesByUser(userId);

        if(userArticles.size() == 0) throw new RuntimeException("게시물이 없습니다.");

        return userArticles;
    }

    /**
     * 도전 과제 정보 가져오기
     * */
    @Override
    public List<GoalAmount> selectChallenge(String userId) {
//        Optional<GoalAmount> goalAmountList = goalAmountRepo.findById(user.getUserSeq()).orElse(null);
//
//        return goalAmountList;
        return null;
    }

    /**
     * 유저 프로필 편집 저장
     * 두 번째 parameter dto로 변경
     * */
    @Override
    public Users insertProfile(String userId, Users updateUser) {
        Users user = userRepo.findByUserId(updateUser.getUserId());

        user.setNickname(updateUser.getNickname());
        user.setIntroduction(updateUser.getIntroduction());

        return userRepo.save(user);
    }

    /**
     * 팔로잉 정보(userSeq) 가져오기
     * */
    @Override
    public List<Long> selectFollowingUserSeq(Long userSeq) {
        Users user = userRepo.findById(userSeq).orElse(null);
        List<Long> userSeqList = followingRepo.findUserSeqThatUserFollows(user);

        return userSeqList;
    }

    @Override
    public ProfileDTO selectFollowingUser(@AuthenticationPrincipal Users loggedInUser, String userId, Long targetUserSeq) {
        Users user = userRepo.findByUserId(userId);
        Users targetUser = userRepo.findById(targetUserSeq).orElse(null);

        System.out.println("****\nloggedInUser\n*****" + loggedInUser.toString());
        System.out.println("****\nuserId\n*****" + userId.toString());
        System.out.println("****\ntargetUserSeq\n*****" + targetUserSeq.toString());

        int status = 0; // user가 targetUser를 팔로우하지 않은 상태

        if(loggedInUser.getUserSeq() == targetUser.getUserSeq()) {
            status = 1; // 본인
        } else {
            Following following = followingRepo.findByFollowingAndFollower(loggedInUser, targetUser.getUserSeq()).orElse(null);
            System.out.println("****\nfollowing\n*****" + following);
            if(following != null) status = 2; //  user가 targetUser를 팔로우 중인 상태
        }

        ProfileDTO profileDTO = ProfileDTO.builder()
                .userSeq(targetUser.getUserSeq())
                .userId(targetUser.getUserId())
                .nickname(targetUser.getNickname())
                .userTier(targetUser.getUserTier())
                .introduction(targetUser.getIntroduction())
                .profileImgUrl(targetUser.getProfileImageUrl())
                .status(status)
                .build();

        return profileDTO;
    }

    /**
     * 팔로잉 정보(List) 가져오기
     * @param userId // 조회할 사용자
     * */
    @Override
    public List<ProfileDTO> selectFollowing(@AuthenticationPrincipal Users loggedInUser, String userId) {
        Users user = userRepo.findByUserId(userId);

        List<Long> userSeqList = selectFollowingUserSeq(user.getUserSeq());
        if (userSeqList == null) {
            throw new RuntimeException("팔로잉 중인 사용자가 없습니다.");
        }

        List<ProfileDTO> userList = new ArrayList<>();
        userSeqList.forEach(u -> userList.add(selectFollowingUser(loggedInUser, userId, u)));

        return userList;
    }
    /**
     * 팔로워 정보 가져오기
     * */
    @Override
    public List<Users> selectFollower(String userId) {
        Users user = userRepo.findByUserId(userId);
        List<Users> followerList = followingRepo.findProfileThatUserFollowing(user);

        return followerList;
    }

    /**
     * 팔로잉 해제
     * */
    @Override
    public Following unfollow(Users user, String targetUserId) throws Exception {
        Users followingUser = userRepo.findByUserId(targetUserId);

        // 팔로우하려는 사용자가 없음.
        if(followingUser == null) {
            throw new RuntimeException("User not found");
        }

        Following f = followingRepo.findByFollowingAndFollower(user, followingUser.getUserSeq()).orElse(null);


        // 서로 팔로잉 관계가 아닐 때
        if(f == null) {
            throw new RuntimeException("User not following " + followingUser.getNickname());
        }

        return followingRepo.save(f);
    }

    /**
     * 팔로우 추가
     * */
    @Override
    public Following follow(Users user, String targetUserId) throws Exception {
        // issue
        // following에서 userseq랑 followingseq unique 체크
        Users followingUser = userRepo.findByUserId(targetUserId);

        // 팔로우하려는 사용자가 없음.
        if(followingUser == null) {
            throw new RuntimeException("User not found!");
        }

        Following f = new Following();
        f.setUser(user);
        f.setFollowingUserSeq(followingUser.getUserSeq());

        // 팔로우 알림 로직
        FollowNotification followNotification = FollowNotification.builder()
                .user(followingUser)
                .fromUser(user)
                .url("http://localhost:3000/profile/profileinfo/" + user.getUserId())
                .notificationDateTime(LocalDateTime.now())
                .build();

        followNotificationRepo.save(followNotification);

        return followingRepo.save(f);
    }
}