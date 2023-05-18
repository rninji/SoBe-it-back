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

    /**
     * 글 1개 조회
     * @param user
     * @param articleSeq
     * @return
     */
    @GetMapping("/detail")
    public ResponseEntity<?> articleDetail(@AuthenticationPrincipal Users user, Long articleSeq){
        try{
            ArticleResponseDTO articleResponseDTO = articleService.articleDetail(user, articleSeq);
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
    public ResponseEntity<?> selectArticleAll(@AuthenticationPrincipal Users user){
        try {
            List<ArticleResponseDTO> articleResponseDTOList = articleService.feed(user);
            return ResponseEntity.ok().body(articleResponseDTOList);
        } catch (Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }
    }

    /**
     * 글 좋아요
     * @param user
     * @param articleSeqMap
     * @return
     */
    @PostMapping("/like")
    public ResponseEntity<?> likeArticle(@AuthenticationPrincipal Users user, @RequestBody Map<String, Long> articleSeqMap){
        try{
            boolean like = articleService.likeArticle(user, articleSeqMap.get("articleSeq"));
            return ResponseEntity.ok().body(like);
        }
        catch(Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }
    }

    /**
     * 투표하기
     * @param user
     * @param voteDTO
     * @return 성공 시 투표 정보 반환
     */
    @PostMapping("/vote")
    public ResponseEntity<?> vote(@AuthenticationPrincipal Users user, @RequestBody VoteDTO voteDTO){
        try{
            Vote vote = articleService.voteArticle(user, voteDTO);
            return ResponseEntity.ok().body(vote);
        } catch(Exception e){
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }

    }


}
