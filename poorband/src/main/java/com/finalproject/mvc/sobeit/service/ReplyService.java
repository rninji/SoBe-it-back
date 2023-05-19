package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.ReplyDTO;
import com.finalproject.mvc.sobeit.dto.ReplyLikeDTO;
import com.finalproject.mvc.sobeit.entity.Reply;
import com.finalproject.mvc.sobeit.entity.Users;

public interface ReplyService {
    public Reply writeReply(final Users user, Long articleNum, Reply reply);

    public Reply updateReply(Reply reply);

    public ReplyDTO deleteReply(final Users user, ReplyDTO replyDTO);

    public ReplyLikeDTO likeReply(final Users user, ReplyLikeDTO replyLikeDTO);
}
