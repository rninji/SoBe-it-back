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
public class ReplyDTO {
    private Long reply_seq;
    private Long article_seq;
    private Long user_seq;
    private String reply_text;
    private Long parent_reply_seq;
    private LocalDateTime written_date;
    private int is_updated;
    private String nickname;
    private String profile_image_url;
    private int reply_like_cnt;
}
