package com.finalproject.mvc.sobeit.repository;

import com.finalproject.mvc.sobeit.dto.FollowDTO;
import com.finalproject.mvc.sobeit.entity.Following;
import com.finalproject.mvc.sobeit.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface FollowingRepo extends JpaRepository<Following, Long>, QuerydslPredicateExecutor<Following> {

    @Query(value = "select count(f) from Following f join Users u on f.user.userSeq = ?1")
    int followingCnt(Long userSeq);

    @Query(value = "select count(f) from Following f join Users u on f.followingUserSeq = ?1")
    int followerCnt(Long userSeq);

    @Query("select u from Users u join Following f where f.user.userSeq = ?1")
    List<Users> findArticleThatUserFollows(Long userSeq);

    @Query("select u from Users u join Following f where f.followingUserSeq = ?1")
    List<Users> findArticleThatUserFollowed(Long userSeq);

    @Query("select f from Following f where f.user.userSeq = ?1 and f.followingUserSeq=?2")
    Optional<Following> findByFollowingAndFollower(Long userSeq, Long followingUserSeq);

}
