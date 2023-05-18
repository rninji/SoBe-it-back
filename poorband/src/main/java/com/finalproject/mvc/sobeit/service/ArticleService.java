package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.ArticleDTO;
import com.finalproject.mvc.sobeit.dto.ArticleResponseDTO;
import com.finalproject.mvc.sobeit.dto.VoteDTO;
import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.ArticleLike;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.entity.Vote;
import com.finalproject.mvc.sobeit.repository.ArticleLikeRepo;
import com.finalproject.mvc.sobeit.repository.ArticleRepo;
import com.finalproject.mvc.sobeit.repository.VoteRepo;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;


public interface ArticleService {
    /**
     * 글 작성
     * @param user
     * @param articleDTO
     * @return 작성된 글
     */
    public Article writeArticle(Users user, ArticleDTO articleDTO);

    /**
     * 글 수정
     * @param user
     * @param articleDTO
     * @return 수정된 글
     */
    public Article updateArticle(Users user, ArticleDTO articleDTO);

    /**
     * 글 삭제
     * @param user
     * @param articleSeq
     */
    public void deleteArticle(Users user, Long articleSeq);

    /**
     * 디테일 페이지
     * @param articleSeq
     * @return
     */
    public ArticleResponseDTO articleDetail(Users user, Long articleSeq);

    /**
     * 아이디로 글 조회
     */
    public Article selectArticleById(Long articleSeq);

    /**
     * 글 하나에 대한 ArticleResponseDTO 가져오기
     * @param userSeq 조회 요청한 유저 번호
     * @param articleSeq 조회하려는 글 번호
     * @return
     */
    public ArticleResponseDTO findArticleResponse(Long userSeq, Long articleSeq);

    /**
     * 피드
     */
    public List<ArticleResponseDTO> feed(Users user);

    /**
     * 피드 글 번호 조회
     * @param userSeq
     * @return 유저가 볼 수 있는 피드 글 번호 리스트
     */
    public List<Long> selectFeedArticleSeq(Long userSeq);

    /**
     * 글 좋아요
     * @param user
     * @param articleSeq
     * @return true : 좋아요, false : 좋아요 취소
     */
    public boolean likeArticle(Users user, Long articleSeq);

    /**
     * 기존 좋아요 확인
     * @param userSeq
     * @param articleSeq
     * @return
     */
    public ArticleLike findArticleLike(Long userSeq, Long articleSeq);

    /**
     * 글 좋아요 수 확인
     */
    public int countArticleLike(Long articleSeq);

    /**
     * 투표하기
     * @param voteDTO
     * @return 생성된 투표
     */
    public Vote voteArticle(Users user, VoteDTO voteDTO);

    /**
     * 해당 사용자의 해당 글에 대한 투표 여부 확인
     * @param userSeq
     * @param articleSeq
     * @return true면 투표한 적 있음 / false면 투표한 적 없음
     */
    public boolean voteCheck(Long userSeq, Long articleSeq);

    /**
     * 투표수, 투표율 확인
     * @param articleSeq
     * @return [0] : 찬성표수, [1] : 반대표수, [2] : 찬성표율, [3] : 반대표율
     */
    public int[] findVoteInfo(Long articleSeq);


}
