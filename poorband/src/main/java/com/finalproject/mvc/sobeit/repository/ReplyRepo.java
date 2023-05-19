package com.finalproject.mvc.sobeit.repository;

import com.finalproject.mvc.sobeit.entity.Reply;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReplyRepo extends JpaRepository<Reply, Long> {
    Reply findByReplySeq(Long replySeq);
}
