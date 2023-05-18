package com.finalproject.mvc.sobeit.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ReplyNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long replyNotificationSeq;
    @ManyToOne
    @JoinColumn
    private Users user;
    /**
     * 알림 클릭시 이동할 url
     */
    @Column(nullable = false)
    private String url;

    @ManyToOne
    @JoinColumn
    private Article article;

    @Column(nullable = false)
    private LocalDateTime notificationDateTime;
}
