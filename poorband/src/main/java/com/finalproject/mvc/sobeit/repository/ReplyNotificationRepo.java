package com.finalproject.mvc.sobeit.repository;

import com.finalproject.mvc.sobeit.entity.LikeNotification;
import com.finalproject.mvc.sobeit.entity.ReplyNotification;
import com.finalproject.mvc.sobeit.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReplyNotificationRepo extends JpaRepository<ReplyNotification, Long> {
    Optional<List<ReplyNotification>> findByUser(Users user);

}
