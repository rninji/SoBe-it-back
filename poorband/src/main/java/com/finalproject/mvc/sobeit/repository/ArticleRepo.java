package com.finalproject.mvc.sobeit.repository;

import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Long> {
    @Query("select a from Article a where a.userSeq = ?1 order by a.writtenDate desc")
    List<Article> findArticlesByUser(String user_id);

    @Query("select a from Article a where a.articleText like %?1%")
    List<Article> findArticlesByArticleText(String articleText);

    Article findByUserId(String userId);
}
