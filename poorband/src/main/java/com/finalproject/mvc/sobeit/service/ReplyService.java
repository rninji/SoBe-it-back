package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.ReplyDTO;
import com.finalproject.mvc.sobeit.dto.ReplyLikeDTO;
import com.finalproject.mvc.sobeit.entity.Reply;
import com.finalproject.mvc.sobeit.entity.Users;

import java.util.List;

public interface ReplyService {
    public Reply writeReply(final Users user, Long articleNum, Reply reply);

    public Reply updateReply(Reply reply);

    public ReplyDTO deleteReply(final Users user, ReplyDTO replyDTO);

    public List<ReplyDTO> selectAllReply(final Users user, Long articleSeq);

    public boolean likeReply(Users user, Long replySeq);
}
