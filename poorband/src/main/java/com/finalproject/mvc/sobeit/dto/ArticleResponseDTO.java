package com.finalproject.mvc.sobeit.dto;

import com.finalproject.mvc.sobeit.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleResponseDTO {
    private Users user;
    private int status;
    private String imageUrl;
    private String expenditureCategory;
    private Long amount;
    private String articleText;
    private String articleType;
    private LocalDate consumptionDate;
    private LocalDateTime writtenDate;
    private String isAllowed;

    // 댓글 수
    private int commentCnt;
    // 좋아요 수
    private int likeCnt;
    // 좋아요 여부
    private boolean isLiked;
    // 투표 여부
    private boolean isVoted;
    // 찬성표수
    private int agree;
    // 반대표수
    private int disagree;
    // 찬성표율
    private int agreeRate;
    // 반대표율
    private int disagreeRate;
}
