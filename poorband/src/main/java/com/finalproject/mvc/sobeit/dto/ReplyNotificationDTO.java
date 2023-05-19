package com.finalproject.mvc.sobeit.dto;

import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyNotificationDTO implements NotificationDTO {
    private Long notificationSeq;
    private int type;
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
