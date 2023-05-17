package com.finalproject.mvc.sobeit.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
    private String profileImg;
    private String nickname;
    private LocalDateTime writtenDate;
    private int status;
    private String articleType;
    private String category;
    private String articleText;
    private Long amount;
}
