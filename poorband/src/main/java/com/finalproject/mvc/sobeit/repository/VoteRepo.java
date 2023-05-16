package com.finalproject.mvc.sobeit.repository;

import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.Vote;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface VoteRepo extends JpaRepository <Vote, Long>{
    @Query("select v from Vote v where v.userSeq =:#{#vote.userSeq} and v.articleSeq =:#{#vote.articleSeq}")
    Optional<Vote> findVoteByUserSeqAndArticleSeq(@Param("vote") Vote vote);
}
