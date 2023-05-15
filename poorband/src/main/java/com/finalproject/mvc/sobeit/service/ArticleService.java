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

}
