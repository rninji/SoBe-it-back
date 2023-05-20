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
public class ArticleLikeNotification {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long ArticleLikeNotificationSeq;
    @ManyToOne
    @JoinColumn(name = "userSeq")
    private Users user;

    /**
     * 좋아요가 발생한 해당 글의 URL
     */
    @Column(nullable = false)
    private String url;

    /**
     * 좋아요 1개 = 1
     * 좋아요 10개 = 2
     * 좋아요 100개 = 3
     */
    @Column(nullable = false)
    private int type;

    @ManyToOne
    @JoinColumn
    private Article notArticleSeq;

    @Column(nullable = false)
    private LocalDateTime notificationDateTime;
}
