package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.dto.ArticleDTO;
import com.finalproject.mvc.sobeit.dto.ResponseDTO;
import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.ArticleLike;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.entity.Vote;
import com.finalproject.mvc.sobeit.service.ArticleService;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/article")
public class ArticleController {
    private final ArticleService articleService;

    /**
     * 글 작성
     * @param user
     * @param articleDTO
     * @return
     */
    @PostMapping("/write")
    public ResponseEntity<?> writeArticle(@AuthenticationPrincipal Users user, @RequestBody ArticleDTO articleDTO){
        System.out.println("좀 떠라");
        try{
            // 요청 이용해 저장할 글 생성
            Article article = Article.builder()
                    .user(user)
                    .status(articleDTO.getStatus())
                    .imageUrl(articleDTO.getImageUrl())
                    .expenditureCategory(articleDTO.getExpenditureCategory())
                    .amount(articleDTO.getAmount())
                    .financialText(articleDTO.getFinancialText())
                    .articleText(articleDTO.getArticleText())
                    .writtenDate(LocalDateTime.now())
                    .articleType(articleDTO.getArticleType())
                    //.consumptionDate(articleDTO.getConsumptionDate())
                    .consumptionDate(LocalDate.now()) // 나중에 위에꺼로 바꾸기
                    .isAllowed(articleDTO.getIsAllowed())
                    .build();

            // 서비스 이용해 글 저장
            Article writtenArticle = articleService.writeArticle(article);

            // 저장된 글 번호 반환 (이것만 반환해도 되겠지?ㅎㅎ)
            Long articleSeq = writtenArticle.getArticleSeq();
            return ResponseEntity.ok().body(articleSeq);
        } catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }

    }

    /**
     * 글 수정
     * @param user
     * @param articleDTO
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateArticle(@AuthenticationPrincipal Users user, @RequestBody ArticleDTO articleDTO){
        try{
            Article article = Article.builder()
                    .user(user)
                    .articleSeq(articleDTO.getArticleSeq())
                    .status(articleDTO.getStatus())
                    .imageUrl(articleDTO.getImageUrl())
                    .expenditureCategory(articleDTO.getExpenditureCategory())
                    .amount(articleDTO.getAmount())
                    .financialText(articleDTO.getFinancialText())
                    .articleText(articleDTO.getArticleText())
                    .writtenDate(LocalDateTime.now())
                    //.articleType(articleDTO.getArticleType()) // 유형은 못 바꾸게 해야될거같음
                    //.consumptionDate(articleDTO.getConsumptionDate())
                    .consumptionDate(LocalDate.now()) // 나중에 위에꺼로 바꾸기
                    .editedDate(LocalDateTime.now())
                    .isAllowed(articleDTO.getIsAllowed())
                    .build();
            Article updatedArticle = articleService.updateArticle(user.getUserSeq(), article);
            if (updatedArticle==null) {
                throw new RuntimeException("글 수정 실패");
            }

            // 업데이트된 글 번호 반환
            Long articleSeq = updatedArticle.getArticleSeq();
            return ResponseEntity.ok().body(articleSeq);
        } catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }
    }

    /**
     * 글 삭제
     * @param user
     * @param articleSeq
     * @return
     */
    @PostMapping("/delete")
    public ResponseEntity<?> deleteArticle(@AuthenticationPrincipal Users user, @RequestBody Map<String, Long> articleSeqMap){
        try{
            articleService.deleteArticle(user.getUserSeq(), articleSeqMap.get("articleSeq"));
            return ResponseEntity.ok().body("success");
        }
        catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }

    }

    ////////// 상세 조회 ArticleResponseDTO 반환하기
    /**
     * 글 상세 조회
     * @param user
     * @param articleSeq
     * @return
     */
    @GetMapping("/detail")
    public JSONObject selectArticleById(@AuthenticationPrincipal Users user, Long articleSeq){
        JSONObject articleData = new JSONObject();

        // 글 조회
        Article article = articleService.selectArticleById(user.getUserSeq(), articleSeq);
        // 예외처리 추가
        articleData.put("article", article);

        // 작성자 여부 체크
        boolean isMine = false;
        if (user.getUserSeq() == article.getUser().getUserSeq()) isMine = true;
        articleData.put("isMine", isMine);

        return articleData;
    }

    /**
     * 글 전체 조회
     * @return
     */
    @GetMapping("/selectAll")
    public List<Article> selectArticleAll(){
        List<Article> list = articleService.selectAllArticle();
        return list;
    }

    /**
     * 글 좋아요
     * @param articleLike
     * @return true면 좋아요 false면 좋아요 삭제
     */
    @PostMapping("/like")
    public boolean likeArticle(ArticleLike articleLike){
        // 좋아요 생성 시 true, 취소 시 false
        boolean isLiked = articleService.likeArticle(articleLike);
        return isLiked;
    }

    /**
     * 투표하기
     * @param vote
     */
    @PostMapping("/vote")
    public void vote(Vote vote){
        if (articleService.voteCheck(vote)){
            // throw Exception("이미 투표 완료했습니다.");
            System.out.println("중복 투표");
            return;
        }

        Vote votedVote = articleService.voteArticle(vote);
        if (votedVote == null) {
            //throw Exception("투표 실패");
        }
        // return ("redirect:/투표한 그 페이지.. 아니면 그냥 프론트에서 처리");
    }

    /**
     * 투표 여부 확인
     */
    public boolean voteCheck(@AuthenticationPrincipal Users user, Long articleSeq){
        return false;
    }

    /**
     * 투표율 확인
     * @param articleSeq
     * @return {"agree": 찬성표수, "disagree": 반대표수, "agreeRate: 찬성표율, "disagreeRate": 반대표율}
     */
    public JSONObject voteRate(Long articleSeq){
        int[] voteValue = articleService.voteCount(articleSeq);
        JSONObject rate = new JSONObject();
        rate.put("agree",voteValue[0]);
        rate.put("disagree",voteValue[1]);
        int agreeRate = 0;
        int disagreeRate = 0;
        if (voteValue[0]!=0 || voteValue[1]!=0) { // 투표수가 0이 아니라면
            agreeRate = voteValue[0]/(voteValue[0]+voteValue[1]) * 100;
            disagreeRate = 100 - agreeRate;
        }
        rate.put("agreeRate", agreeRate);
        rate.put("disagreeRate", disagreeRate);
        return rate;
    }
}
