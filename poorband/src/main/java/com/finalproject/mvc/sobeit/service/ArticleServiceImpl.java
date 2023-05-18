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

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepo articleRepo;
    private final ArticleLikeRepo articleLikeRepo;
    private final VoteRepo voteRepo;

    /**
     * 글 작성
     * @param article
     */
    public Article writeArticle(Article article) {
        return articleRepo.save(article);
    }

    /**
     * 글 수정
     * @param userSeq
     * @param article
     * @return
     */
    public Article updateArticle(Long userSeq, Article article) {
        Article existingArticle = articleRepo.findById(article.getArticleSeq()).orElse(null); // 기존 작성글 가져오기
        if (existingArticle==null) { // 수정할 글이 없는 경우 예외 발생
            throw new RuntimeException("수정할 글이 없습니다.");
        }
        if (userSeq != existingArticle.getUser().getUserSeq()){ // 기존 글의 작성자가 아니면 예외 발생
            throw new RuntimeException("글의 작성자가 아닙니다.");
        }

        article.setWrittenDate(existingArticle.getWrittenDate()); // 작성시간 복사
        article.setArticleType(existingArticle.getArticleType()); // 타입 복사
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
            throw new RuntimeException("삭제할 글이 없습니다.");
        }

        if (userSeq!=foundArticle.getUser().getUserSeq()){ // 삭제 요청 유저가 작성자가 아닐 경우 예외 발생
            throw new RuntimeException("작성자가 아닙니다.");
        }
        articleRepo.deleteById(articleSeq);
    }

    /**
     * 글 아이디로 조회
     * @param articleSeq
     * @return
     */
    public Article selectArticleById(Long articleSeq) {
        Article foundArticle = articleRepo.findById(articleSeq).orElse(null);
        if (foundArticle == null){ // 글이 없는 경우 예외 발생
            throw new RuntimeException("글이 존재하지 않습니다.");
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
     * @param userSeq
     * @param articleSeq
     * @return true면 투표한 적 있음 / false면 투표한 적 없음
     */
    public boolean voteCheck(Long userSeq, Long articleSeq){
        Vote existingVote = voteRepo.findVoteByUserSeqAndArticleSeq(userSeq, articleSeq).orElse(null);
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
