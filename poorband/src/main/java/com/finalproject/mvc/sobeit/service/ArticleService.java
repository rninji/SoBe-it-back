package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.repository.ArticleRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
@Service
public class ArticleService {
    @Autowired
    ArticleRepo articleRepo;

    /**
     * 글 작성
     * @param article
     */
    public void writeArticle(Article article) {
        article.setWritten_date(LocalDateTime.now());
        articleRepo.save(article);
    }

    /**
     * 글 수정
     */
    public void updateArticle(Article article) {
        article.setEdited_date(LocalDateTime.now());
        article.setWritten_date(LocalDateTime.now()); // 작성일 null이 안됨.. select해와서 다시 저장하는 방법 말고 유지시키는 방법 없나?
        articleRepo.save(article);
    }

}
