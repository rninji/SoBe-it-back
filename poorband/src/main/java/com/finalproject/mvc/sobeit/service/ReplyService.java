package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.Reply;
import com.finalproject.mvc.sobeit.entity.Users;

public interface ReplyService {
    public Reply writeReply(final Users user, Long articleNum, Reply reply);

    public Reply updateReply(Reply reply);

    public void deleteReply(Long id);
}
