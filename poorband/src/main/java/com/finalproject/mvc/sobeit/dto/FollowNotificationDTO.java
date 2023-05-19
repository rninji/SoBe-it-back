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
public class FollowNotificationDTO implements NotificationDTO {
    private Long notificationSeq;
    private int type;
    private String followingUserNickName;
    private String followingUserId;
    private String content;
    private boolean isFollowing;
    private String url;
    private String imageUrl;
    private LocalDateTime timestamp;
    @Override
    public LocalDateTime getTimestamp() {
        return null;
    }

}
