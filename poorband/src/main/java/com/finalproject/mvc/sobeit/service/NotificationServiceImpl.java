package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.NotificationDTO;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.repository.LikeNotificationRepo;
import com.finalproject.mvc.sobeit.repository.ReplyNotificationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {
    private final LikeNotificationRepo likeNotificationRepo;
    private final ReplyNotificationRepo replyNotificationRepo;

    @Override
    public List<NotificationDTO> getAllNotification(Users user) {
        return null;
    }

    @Override
    public void deleteOneNotice(Users user, Long notificationSeq) {

    }

    @Override
    public void deleteAllNotice(Users user) {

    }
}
