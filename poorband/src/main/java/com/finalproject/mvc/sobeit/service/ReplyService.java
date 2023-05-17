package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.Reply;

public interface ReplyService {
    public Reply writeReply(Reply reply);

    public Reply updateReply(Reply reply);

    public void deleteReply(Long id);
}
