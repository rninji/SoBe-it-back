package com.finalproject.mvc.sobeit.repository;

import com.finalproject.mvc.sobeit.entity.FollowNotification;
import com.finalproject.mvc.sobeit.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

public interface FollowNotificationRepo extends JpaRepository<FollowNotification, Long> {

    Optional<List<FollowNotification>> findByUser(Users user);
}
