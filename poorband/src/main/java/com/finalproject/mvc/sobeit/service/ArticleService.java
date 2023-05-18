package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.ArticleLike;
import com.finalproject.mvc.sobeit.entity.Vote;
import com.finalproject.mvc.sobeit.repository.ArticleLikeRepo;
import com.finalproject.mvc.sobeit.repository.ArticleRepo;
import com.finalproject.mvc.sobeit.repository.VoteRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


public interface ArticleService {
    /**
     * 글 작성
     * @param article
     */
    public Article writeArticle(Article article);

    /**
     * 글 수정
     * @param userSeq
     * @param article
     * @return
     */
    public Article updateArticle(Long userSeq, Article article);

    /**
     * 글 삭제
     * @param userSeq
     * @param articleSeq
     */
    public void deleteArticle(Long userSeq, Long articleSeq);

    /**
     * 글 아이디로 조회
     * @param articleSeq
     * @return
     */
    public Article selectArticleById(Long articleSeq);

    /**
     * 글 전체 조회
     * @return
     */
    public List<Article> selectAllArticle();

    /**
     * 글 좋아요
     * @param articleLike
     */
    public boolean likeArticle(ArticleLike articleLike);

    /**
     * 투표하기
     * @param vote
     * @return 생성된 투표
     */
    public Vote voteArticle(Vote vote);

    /**
     * 해당 사용자의 해당 글에 대한 투표 여부 확인
     * @param userSeq
     * @param articleSeq
     * @return true면 투표한 적 있음 / false면 투표한 적 없음
     */
    public boolean voteCheck(Long userSeq, Long articleSeq);

    /**
     * 투표수 확인
     * @param articleSeq
     * @return v[0] 찬성표수, v[1] 반대표수
     */
    public int[] voteCount(Long articleSeq);
}
