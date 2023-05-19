package com.finalproject.mvc.sobeit.dto;

import java.time.LocalDateTime;

public class ReplyNotificationDTO implements NotificationDTO {
    private Long notificationSeq;
    private String content;
    private String articleContent;
    private String url;
    private String imageUrl;
    private LocalDateTime timestamp;

    @Override
    public LocalDateTime getTimestamp() {
        return null;
    }
}
