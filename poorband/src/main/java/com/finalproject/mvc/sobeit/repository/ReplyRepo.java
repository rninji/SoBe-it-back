package com.finalproject.mvc.sobeit.repository;

import com.finalproject.mvc.sobeit.entity.Reply;
import com.finalproject.mvc.sobeit.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReplyRepo extends JpaRepository<Reply, Long> {
    Reply findByReplySeq(Long replySeq);

    Reply findByReplySeqAndUser(Long replySeq, Users user);

    Boolean existsByReplySeqAndUser(Long replySeq, Users user);

    @Query("select count(*) from Reply r where r.article.articleSeq = ?1")
    int findReplyCountByArticleSeq(Long articleSeq);
}
