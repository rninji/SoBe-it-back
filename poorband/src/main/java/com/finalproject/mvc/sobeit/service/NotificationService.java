package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.NotificationDTO;
import com.finalproject.mvc.sobeit.entity.Users;

import java.util.List;

public interface NotificationService {
    List<NotificationDTO> getAllNotification(Users user);
    void deleteOneNotice(Users user, Long notificationSeq);
    void deleteAllNotice(Users user);
}
