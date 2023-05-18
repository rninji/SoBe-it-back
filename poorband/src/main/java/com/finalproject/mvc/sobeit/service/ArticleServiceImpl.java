package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.ArticleResponseDTO;
import com.finalproject.mvc.sobeit.entity.*;
import com.finalproject.mvc.sobeit.repository.ArticleLikeRepo;
import com.finalproject.mvc.sobeit.repository.ArticleRepo;
import com.finalproject.mvc.sobeit.repository.ReplyRepo;
import com.finalproject.mvc.sobeit.repository.VoteRepo;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ArticleServiceImpl implements ArticleService{

    private final ArticleRepo articleRepo;
    private final ArticleLikeRepo articleLikeRepo;
    private final VoteRepo voteRepo;
    private final ReplyRepo replyRepo;

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
    public void deleteArticle(Long userSeq, Long articleSeq) throws RuntimeException {
        Article foundArticle = articleRepo.findById(articleSeq).orElse(null);
        if (foundArticle==null){ // 삭제할 글이 없는 경우
            throw new RuntimeException("삭제할 글이 없습니다.");
        }

        if (userSeq!=foundArticle.getUser().getUserSeq()){ // 삭제 요청 유저가 작성자가 아닐 경우 예외 발생
            throw new RuntimeException("작성자가 아닙니다.");
        }
        articleRepo.deleteById(articleSeq);
    }
    //////////////////////////////////////////////////////////////////

    /**
     * 디테일 페이지
     * @param articleSeq
     * @return
     */
    @Override
    public ArticleResponseDTO articleDetail(Users user, Long articleSeq) throws RuntimeException{
        Article article = selectArticleById(articleSeq);

        // 글에 대한 권한 확인
        //if (article.getStatus()==2 && !맞팔체크(user.getUserSeq(), article.getUser()){
        //    throw new RuntimeException("맞팔로우의 유저만 확인 가능한 글입니다.");
        //}
        //else
        if(article.getStatus()==3 && user.getUserId() != article.getUser().getUserId()){
            throw new RuntimeException("비공개 글입니다.");
        }

        // ArticleResponseDTO 반환
        return findArticleResponse(user.getUserSeq(), articleSeq);
    }

    /**
     * 아이디로 글 조회
     * @param articleSeq
     * @return 해당 번호 글
     */
    @Override
    public Article selectArticleById(Long articleSeq) {
        return articleRepo.findById(articleSeq).orElse(null);
    }

    /**
     * 글 하나에 대한 ArticleResponseDTO 가져오기
     * @param userSeq 조회 요청한 유저 번호
     * @param articleSeq 조회하려는 글 번호
     * @return
     */
    @Override
    public ArticleResponseDTO findArticleResponse(Long userSeq, Long articleSeq) {
        // 보려는 글 가져오기
        Article article = selectArticleById(articleSeq);

        if (article == null){ // 글이 없는 경우 예외 발생
            throw new RuntimeException("글이 존재하지 않습니다.");
        }

        // 댓글 수 가져오기
        int replyCnt = 0;
        // int replyCnt = replyRepo.findReplyCountByArticleSeq(articleSeq);

        // 좋아요 수 가져오기
        int likeCnt = countArticleLike(articleSeq);

        // 좋아요 여부 확인
        ArticleLike articleLike = findArticleLike(userSeq, articleSeq);
        boolean isLiked = true;
        if (articleLike==null) isLiked = false;

        // 투표율 가져오기
        int [] voteInfo = findVoteInfo(articleSeq);

        // 투표여부 가져오기
        boolean isVoted = voteCheck(userSeq, articleSeq);

        // ArticleResponseDTO 반환
        ArticleResponseDTO articleResponseDTO = ArticleResponseDTO.builder()
                .user(article.getUser())
                .status(article.getStatus())
                .imageUrl(article.getImageUrl())
                .expenditureCategory(article.getExpenditureCategory())
                .amount(article.getAmount())
                .articleType(article.getArticleType())
                .consumptionDate(article.getConsumptionDate())
                .writtenDate(article.getWrittenDate())
                .isAllowed(article.getIsAllowed())
                .commentCnt(replyCnt)
                .likeCnt(likeCnt)
                .isLiked(isLiked)
                .isVoted(isVoted)
                .agree(voteInfo[0]) // 찬성표수
                .disagree(voteInfo[1]) // 반대표수
                .agreeRate(voteInfo[2]) // 찬성표율
                .disagreeRate(voteInfo[3]) // 반대표율
                .build();

        return articleResponseDTO;
    }

    /**
     * 피드
     * @param user
     * @return
     */
    @Override
    public List<ArticleResponseDTO> feed(Users user){
        // 권한에 맞는 글번호 리스트 가져오기
        // ArticleResponseDTO 가져오기
        return null;
    }

    /**
     * 피드 글 번호 조회
     * @param userSeq
     * @return 유저가 볼 수 있는 권한의 글번호 리스트 최신순
     */
    @Override
    public List<Long> selectFeedArticleSeq(Long userSeq) {
        //
        return null;
    }
    //////////////////////////////////////////////////////////////////

    /**
     * 글 좋아요
     * @param user
     * @param articleSeq
     * @return
     * @throws RuntimeException
     */
    public boolean likeArticle(Users user, Long articleSeq) throws RuntimeException{
        ArticleLike existingLike = findArticleLike(user.getUserSeq(), articleSeq); // 기존 좋아요가 있는 지 확인
        if (existingLike==null){ // 좋아요한 적 없으면 좋아요 생성
            ArticleLike articleLike = ArticleLike.builder()
                    .article(selectArticleById(articleSeq))
                    .user(user)
                    .build();
            articleLikeRepo.save(articleLike);
            return true;
        }
        else { // 좋아요한 적 있으면 좋아요 취소(삭제)
            articleLikeRepo.delete(existingLike);
            return false;
        }
    }

    /**
     * 기존 좋아요 확인
     * @param userSeq
     * @param articleSeq
     * @return
     */
    @Override
    public ArticleLike findArticleLike(Long userSeq, Long articleSeq) {
        return articleLikeRepo.findArticleLikeByUserSeqAndArticleSeq(userSeq, articleSeq).orElse(null);
    }

    /**
     * 글 좋아요 수 확인
     * @param articleSeq
     * @return 좋아요 수
     */
    @Override
    public int countArticleLike(Long articleSeq){
        return articleLikeRepo.findCountArticleLikeByArticleSeq(articleSeq);
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
     * 투표수, 투표율 확인
     * @param articleSeq
     * @return [0] : 찬성표수, [1] : 반대표수, [2] : 찬성표율, [3] : 반대표율
     */
    public int[] findVoteInfo(Long articleSeq){
        int[] voteInfo = new int [4];
        // 투표수 확인
        voteInfo[0] = voteRepo.findAgreeCountByArticleSeq(articleSeq);
        voteInfo[1] = voteRepo.findDisagreeCountByArticleSeq(articleSeq);

        // 투표율 계산
        int agreeRate = 0;
        int disagreeRate = 0;
        if (voteInfo[0]!=0 || voteInfo[1]!=0) { // 투표수가 0이 아니라면
            agreeRate = voteInfo[0]/(voteInfo[0]+voteInfo[1]) * 100;
            disagreeRate = 100 - agreeRate;
        }
        voteInfo[2] = agreeRate;
        voteInfo[3] = disagreeRate;
        return voteInfo;
    }


}
