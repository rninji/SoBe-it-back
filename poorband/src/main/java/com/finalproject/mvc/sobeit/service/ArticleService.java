package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.ArticleLike;
import com.finalproject.mvc.sobeit.entity.Vote;
import com.finalproject.mvc.sobeit.repository.ArticleLikeRepo;
import com.finalproject.mvc.sobeit.repository.ArticleRepo;
import com.finalproject.mvc.sobeit.repository.VoteRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
@RequiredArgsConstructor
public class ArticleService {

    private final ArticleRepo articleRepo;
    private final ArticleLikeRepo articleLikeRepo;
    private final VoteRepo voteRepo;

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
    public Article updateArticle(Long userSeq, Article article) {
        Article existingArticle = articleRepo.findById(article.getArticleSeq()).orElse(null); // 기존 작성글 가져오기

        if (existingArticle==null) { // 수정할 글이 없는 경우 예외 발생
            // throws new Exception("수정할 글이 없습니다.");
            return null;
        }
        if (userSeq != existingArticle.getUserSeq().getUserSeq()){ // 기존 글의 작성자가 아니면 예외 발생
            //throws new Exception("글의 작성자가 아닙니다.");
            return null;
        }

        article.setWrittenDate(existingArticle.getWrittenDate()); // 작성시간 복사
        article.setEditedDate(LocalDateTime.now()); // 수정시간 등록
        return articleRepo.save(article);
    }

    /**
     * 글 삭제
     * @param userSeq
     * @param articleSeq
     */
    public void deleteArticle(Long userSeq, Long articleSeq) {
        Article foundArticle = articleRepo.findById(articleSeq).orElse(null);
        if (foundArticle==null){ // 삭제할 글이 없는 경우
            // thorw new Exception("삭제할 글이 없습니다.");
        }

        if (userSeq!=foundArticle.getUserSeq().getUserSeq()){ // 삭제 요청 유저가 작성자가 아닐 경우 예외 발생
            // thorw new Exception("작성자가 아닙니다.");
        }
        articleRepo.deleteById(articleSeq);
    }

    /**
     * 글 상세 조회
     * @param userSeq
     * @param articleSeq
     * @return
     */
    public Article selectArticleById(Long userSeq, Long articleSeq) {
        Article foundArticle = articleRepo.findById(articleSeq).orElse(null);
        if (foundArticle == null){ // 글이 없는 경우 예외 발생
            //throw new Exception("글이 존재하지 않습니다.");
            return null;
        }
        if (foundArticle.getStatus()==3 && userSeq!=foundArticle.getUserSeq().getUserSeq()){ // 권한이 없는 경우 - 비공개 글 & 내 글 아님
            //throw new Exception("글을 조회할 권한이 없습니다.");
        }
        // if (foundArticle.getStatus()==2 && 맞팔확인) // 권한이 없는 경우 - 맞팔 공갠데 맞팔이 아님
        {
            //throw new Exception("글을 조회할 권한이 없습니다.");
        }
        return foundArticle;
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
