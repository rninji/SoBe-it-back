package com.finalproject.mvc.sobeit.repository;

import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


public interface ArticleRepo extends JpaRepository<Article, Long> {
    @Query("select a from Article a where a.user.userId = ?1 order by a.writtenDate desc")
    List<Article> findArticlesByUser(String user_id);

    @Query("select a from Article a where a.articleText like %?1%")
    List<Article> findArticlesByArticleText(String articleText);


    Article findByArticleSeq(Long articleSeq);


    @Query("SELECT u, a FROM Users u LEFT JOIN Article a ON a.user =:user")
    List<Article[]> getArticlesByUser(@Param("user") Users user);

   // 피드에 들어가는 글번호 최신순으로 가져오기
    // 내가 팔로우한 유저의 전체공개 글 + 맞팔인 유저의 맞팔공개 글 + 내 글
    @Query(value = "SELECT a.article_Seq FROM Article a WHERE a.user_Seq IN (" +
            "        SELECT u.user_seq FROM Users u JOIN Following f ON u.user_Seq= f.following_user_Seq WHERE f.user_Seq = 1) AND a.status=1 " +
            "OR a.user_Seq IN (select f2.user_seq from (select * from following f WHERE f.user_Seq = 1) f1 join (select * from following where f.following_user_seq = 1) f2 on f1.following_user_seq = f2.user_seq) AND a.status=2 " +
            "OR a.user_Seq = 1 order by written_date desc", nativeQuery = true)
    List<Long> getArticleSeqListInFeed(Long userSeq);
}
