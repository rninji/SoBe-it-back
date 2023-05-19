package com.finalproject.mvc.sobeit.dto;

import java.time.LocalDateTime;

public class ReplyLikeNotificationDTO implements NotificationDTO {
    private Long notificationSeq;
    private String content;
    private String url;
    private String imageUrl;
    private String articleContent;
    private LocalDateTime timestamp;

    @Override
    public LocalDateTime getTimestamp() {
        return null;
    }}
