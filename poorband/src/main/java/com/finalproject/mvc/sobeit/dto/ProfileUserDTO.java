package com.finalproject.mvc.sobeit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProfileUserDTO {
    private String profileImg;
    private String nickname;
    private String userId;
    private String introDetail;
    private int followingCnt;
    private int followerCnt;
}
