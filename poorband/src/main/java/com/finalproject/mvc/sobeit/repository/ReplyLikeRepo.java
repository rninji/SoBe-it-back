package com.finalproject.mvc.sobeit.repository;

import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.Reply;
import com.finalproject.mvc.sobeit.entity.ReplyLike;
import com.finalproject.mvc.sobeit.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReplyLikeRepo extends JpaRepository<ReplyLike, Long> {
    ReplyLike findByReplyAndUser(Reply reply, Users user);

    Optional<Long> countByReply(Reply reply);
}
