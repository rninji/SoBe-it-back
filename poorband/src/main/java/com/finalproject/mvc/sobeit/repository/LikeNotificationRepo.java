package com.finalproject.mvc.sobeit.repository;

import com.finalproject.mvc.sobeit.entity.ArticleLikeNotification;
import com.finalproject.mvc.sobeit.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikeNotificationRepo extends JpaRepository<ArticleLikeNotification, Long> {
    Optional<List<ArticleLikeNotification>> findByUser(Users user);

}
