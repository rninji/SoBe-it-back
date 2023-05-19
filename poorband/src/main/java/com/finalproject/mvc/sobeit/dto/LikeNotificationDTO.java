package com.finalproject.mvc.sobeit.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LikeNotificationDTO implements NotificationDTO {
    private Long notificationSeq;
    private int type;
    private String content;
    private String url;
    private String imageUrl;
    private String articleContent;
    private LocalDateTime timestamp;

    @Override
    public LocalDateTime getTimestamp() {
        return null;
    }
}
