package com.finalproject.mvc.sobeit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileDTO {
    private int userSeq;
    private String userId;
    private String nickname;
    private String userTier;
    private int introduction;
    private int profileImgUrl;
    private int status; // 전체 공개, 맞팔 공개, 비공개
}
