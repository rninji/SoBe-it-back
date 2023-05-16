package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ArticleController {
    @Autowired
    ArticleService articleService;

    /**
     * 글 작성
     * @param article
     * @return
     */
    @RequestMapping("/article/write")
    public String writeArticle(Article article){
        articleService.writeArticle(article);
        return("write");
    }

    /**
     * 글 수정
     * @param article
     * @return
     */
    @RequestMapping("article/update")
    public String updateArticle(Article article){
        articleService.updateArticle(article);
        return("update");
    }

    /**
     * 글 삭제
     * @param articleSeq
     * @return
     */
    @RequestMapping("/article/delete")
    public String deleteArticle(Long articleSeq){
        articleService.deleteArticle(articleSeq);
        return("delete");
    }

    /**
     * 글 상세 페이지
     * @param articleSeq
     * @return
     */
    @RequestMapping("/article/detail")
    public Article selectArticleById(Long articleSeq){
        Article article = articleService.selectArticleById(articleSeq);
        if (article == null){
            //throw new Exception("글이 존재하지 않습니다.");
            return null;
        }
        return article;
    }

    /**
     * 글 전체 조회
     * @return
     */
    @RequestMapping("/article/selectAll")
    public List<Article> selectArticleAll(){
        List<Article> list = articleService.selectAllArticle();
        return list;
    }

}
