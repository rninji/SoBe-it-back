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

@Repository
public interface FollowingRepo extends JpaRepository<Following, Long>, QuerydslPredicateExecutor<Following> {

    @Query(value = "select count(f) from Following f join Users u on f.user = ?1")
    int followingCnt(Users user);

    @Query(value = "select count(f) from Following f join Users u on f.followingUserSeq = ?1")
    int followerCnt(Long userSeq);

    @Query("select f from Following f where f.user = ?1")
    List<Following> findArticleThatUserFollows(Users user);

    @Query("select f from Following f where f.user = ?1 and f.followingUserSeq=?2")
    Optional<Following> findByFollowingAndFollower(Users followee, Users followingUser_seq);

}
