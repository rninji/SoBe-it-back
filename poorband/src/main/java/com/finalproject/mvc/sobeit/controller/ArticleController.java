package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.dto.ArticleDTO;
import com.finalproject.mvc.sobeit.dto.ArticleResponseDTO;
import com.finalproject.mvc.sobeit.dto.ResponseDTO;
import com.finalproject.mvc.sobeit.dto.VoteDTO;
import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.ArticleLike;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.entity.Vote;
import com.finalproject.mvc.sobeit.service.ArticleServiceImpl;
import lombok.RequiredArgsConstructor;
import org.json.simple.JSONObject;
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
    private final ArticleServiceImpl articleService;

    /**
     * 글 작성
     * @param user
     * @param articleDTO
     * @return 성공 시 작성된 글 번호
     */
    @PostMapping("/write")
    public ResponseEntity<?> writeArticle(@AuthenticationPrincipal Users user, @RequestBody ArticleDTO articleDTO){
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
     * @return 성공 시 업데이트된 글 번호
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
     * @param { "articleSeq": 삭제할 글번호}
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
     * 글 1개 조회
     * @param user
     * @param articleSeq
     * @return
     */
    @GetMapping("/detail")
    public ResponseEntity<?> selectArticleById(@AuthenticationPrincipal Users user, Long articleSeq){
        try{
            // 보려는 글 가져오기
            Article article = articleService.selectArticleById(articleSeq);
            // 글에 대한 권한 확인
            //if (article.getStatus()==2 && !맞팔체크){
            //    throw new RuntimeException("맞팔로우의 유저만 확인 가능한 글입니다.");
            //}
            //else
            if(article.getStatus()==3 && user.getUserId() != article.getUser().getUserId()){
                throw new RuntimeException("비공개 글입니다.");
            }
            // ArticleResponseDTO 가져오기
            ArticleResponseDTO articleResponseDTO = findArticleResponse(articleSeq);
            return ResponseEntity.ok().body(articleResponseDTO);
        } catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }
    }

    /**
     * 글 전체 조회
     * @return
     */
    @GetMapping("/selectAll")
    public ResponseDTO<?> selectArticleAll(@AuthenticationPrincipal Users user){
        List<Article> list = articleService.selectAllArticle();
        // 권한에 맞는 글번호 리스트 가져오기
        // ArticleResponseDTO 가져오기
        return null;
    }

    /**
     * 글 하나에 대한 ArticleResponseDTO 가져오기
     */
    public ArticleResponseDTO findArticleResponse(Long articleSeq) {
        return null;
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
     * @param user
     * @param voteDTO
     * @return 투표 성공 시 "success"
     */
    @PostMapping("/vote")
    public ResponseEntity<?> vote(@AuthenticationPrincipal Users user, @RequestBody VoteDTO voteDTO){
        try{
            // 결재 글이 맞는 지 확인
//            if (articleService.selectArticleById(voteDTO.getArticleSeq()).getArticleType()!=결재타입){
//                throw new RuntimeException("투표가 가능한 글이 아닙니다.");
//            }
            System.out.println(1);
            // 이 사용자가 이 글에 투표한 적이 있는 지 확인
            if (articleService.voteCheck(user.getUserSeq(), voteDTO.getArticleSeq())){
                throw new RuntimeException("이미 투표 완료했습니다.");
            }
            System.out.println(2);
            // 투표 생성
            Vote vote = Vote.builder()
                    .article(articleService.selectArticleById(voteDTO.getArticleSeq()))
                    .user(user)
                    .vote(voteDTO.getVoteType())
                    .build();
            System.out.println(3);
            // 서비스 이용해서 투표하기
            Vote votedVote = articleService.voteArticle(vote);

            if (votedVote == null) {
                throw new RuntimeException("투표 실패");
            }
            System.out.println(4);
            // 성공 여부 반환
            return ResponseEntity.ok().body("success");
        } catch(Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }

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

    /**
     * 좋아요 수 확인
     */
    public int countLike(Long articleSeq){

        return 0;
    }
}
