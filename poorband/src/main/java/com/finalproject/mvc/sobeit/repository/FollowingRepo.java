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

    @Query(value = "select count(u) from Users u where u.userId = ?1")
    int followingCnt(String userId);

    @Query("select f from Following f where f.user = ?1")
    List<Following> findArticleThatUserFollows(Users user);

    @Query("select f from Following f where f.user = ?1 and f.followingUserSeq=?2")
    Optional<Following> findByFollowingAndFollower(Users followee, Users followingUser_seq);

}
