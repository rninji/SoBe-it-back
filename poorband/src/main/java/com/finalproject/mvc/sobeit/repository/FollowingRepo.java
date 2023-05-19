package com.finalproject.mvc.sobeit.repository;

import com.finalproject.mvc.sobeit.dto.FollowDTO;
import com.finalproject.mvc.sobeit.entity.Following;
import com.finalproject.mvc.sobeit.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


public interface FollowingRepo extends JpaRepository<Following, Long>, QuerydslPredicateExecutor<Following> {

    @Query(value = "select count(f) from Following f where f.user.userSeq = :#{#user.userSeq}")
    int followingCnt(@Param("user") Users user);

    @Query(value = "select count(f) from Following f where f.followingUserSeq = :#{#user.userSeq}")
    int followerCnt(@Param("user") Users user);

    @Query("select u from Users u join Following f on u.userSeq = f.user.userSeq where f.user.userSeq = :#{#user.userSeq}")
    List<Following> findArticleThatUserFollows(@Param("user") Users user);

    @Query("select f from Following f where f.user.userSeq = :#{#targetUser.userSeq} and f.followingUserSeq=:#{#user.userSeq}")
    Optional<Following> findByFollowingAndFollower(@Param("user") Users user, @Param("targetUser") Users targetUser);

}
