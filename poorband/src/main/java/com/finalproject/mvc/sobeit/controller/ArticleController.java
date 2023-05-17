package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.ArticleLike;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.entity.Vote;
import com.finalproject.mvc.sobeit.service.ArticleService;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/article")
public class ArticleController {
    @Autowired
    ArticleService articleService;

    /**
     * 글 작성
     * @param user
     * @param article
     * @return
     */
    @PostMapping("/write")
    public String writeArticle(@AuthenticationPrincipal Users user, Article article){
        article.setUserSeq(user); // 작성자 등록
        Article writtenArticle = articleService.writeArticle(article);
        if (writtenArticle==null) {
            // throw Exception("글 작성 실패");
            return ("fail");
        }
        return("success");
    }

    /**
     * 글 수정
     * @param user
     * @param article
     * @return
     */
    @PostMapping("/update")
    public String updateArticle(@AuthenticationPrincipal Users user, Article article){
        Article updatedArticle = articleService.updateArticle(user.getUserSeq(), article);
        if (updatedArticle==null) {
            //throw Exception("글 수정 실패");
            return ("fail");
        }
        return("success");
    }

    /**
     * 글 삭제
     * @param user
     * @param articleSeq
     * @return
     */
    @PostMapping("/delete")
    public String deleteArticle(@AuthenticationPrincipal Users user, Long articleSeq){
        articleService.deleteArticle(user.getUserSeq(), articleSeq);
        // 예외 처리
        return("success");
    }

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
        if (user == article.getUserSeq()) isMine = true;
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

    // 투표 여부에 따라 보여지는 글 형태가 다르니까 프론트한테 알려줘야된다면 만들어야 됨
    /**
     * 투표 여부 확인
     */
    @PostMapping("/voteCheck")
    public void voteCheck(){}

    /**
     * 투표율 확인
     * @param articleSeq
     * @return {"agree": 찬성표수, "disagree": 반대표수, "agreeRate: 찬성표율, "disagreeRate": 반대표율}
     */
    @PostMapping("/voteRate")
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
