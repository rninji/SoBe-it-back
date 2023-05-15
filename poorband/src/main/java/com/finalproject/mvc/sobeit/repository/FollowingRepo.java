package com.finalproject.mvc.sobeit.repository;

import com.finalproject.mvc.sobeit.entity.Following;
import com.finalproject.mvc.sobeit.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FollowingRepo extends JpaRepository<Following, Long> {

    // jpa cnt 문법으로 고치기
    @Query(value = "select u from Users u where u.user_id = ?1")
    int followingCnt(String userId);

}
