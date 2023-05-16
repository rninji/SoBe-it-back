package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.ArticleLike;
import com.finalproject.mvc.sobeit.entity.Vote;
import com.finalproject.mvc.sobeit.repository.ArticleLikeRepo;
import com.finalproject.mvc.sobeit.repository.ArticleRepo;
import com.finalproject.mvc.sobeit.repository.VoteRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ArticleService {
    @Autowired
    ArticleRepo articleRepo;
    @Autowired
    ArticleLikeRepo articleLikeRepo;
    @Autowired
    VoteRepo voteRepo;

    /**
     * 글 작성
     * @param article
     */
    public Article writeArticle(Article article) {
        article.setWrittenDate(LocalDateTime.now());
        return articleRepo.save(article);
    }

    /**
     * 글 수정
     */
    public Article updateArticle(Article article) {
        article.setEditedDate(LocalDateTime.now());
        article.setWrittenDate(LocalDateTime.now()); // 작성일 null이 안됨.. select해와서 다시 저장하는 방법 말고 유지시키는 방법 없나?
        return articleRepo.save(article);
    }

    /**
     * 글 삭제
     * @param id
     */
    public void deleteArticle(Long articleSeq) {
        articleRepo.deleteById(articleSeq);
    }

    /**
     * 글 상세 조회
     * @param id
     * @return
     */
    public Article selectArticleById(Long articleSeq) {
        return articleRepo.findById(articleSeq).orElse(null);
    }

    /**
     * 글 전체 조회
     * @return
     */
    public List<Article> selectAllArticle() {
        return articleRepo.findAll();
    }

    /**
     * 글 좋아요
     * @param articleLike
     */
    public boolean likeArticle(ArticleLike articleLike){
        boolean isLiked = false;
        ArticleLike existingLike = articleLikeRepo.findById(articleLike.getLikeSeq()).orElse(null); // 기존 좋아요가 있는 지 확인
        if (existingLike==null){ // 좋아요한 적 없으면 좋아요 생성
            articleLikeRepo.save(articleLike);
            isLiked = true;
        }
        else { // 좋아요한 적 있으면 좋아요 취소(삭제)
            articleLikeRepo.delete(existingLike);
        }
        return isLiked;
    }

    /**
     * 투표하기
     * @param vote
     * @return 생성된 투표
     */
    public Vote voteArticle(Vote vote){
        Vote votedVote = voteRepo.save(vote);
        return votedVote;
    }

    /**
     * 해당 사용자의 해당 글에 대한 투표 여부 확인
     * @param vote
     * @return true면 투표한 적 있음 / false면 투표한 적 없음
     */
    public boolean voteCheck(Vote vote){
        Vote existingVote = voteRepo.findVoteByUserSeqAndArticleSeq(vote).orElse(null);
        if (existingVote==null) return false;
        return true;
    }

    /**
     * 투표수 확인
     * @param articleSeq
     * @return v[0] 찬성표수, v[1] 반대표수
     */
    public int[] voteCount(Long articleSeq){
        int[] voteValue = new int[2];
        voteValue[0] = voteRepo.findAgreeCountByArticleSeq(articleSeq);
        voteValue[1] = voteRepo.findDisagreeCountByArticleSeq(articleSeq);
        return voteValue;
    }
}
