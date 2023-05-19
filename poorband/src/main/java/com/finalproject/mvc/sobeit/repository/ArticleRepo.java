package com.finalproject.mvc.sobeit.repository;

import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Long> {
    @Query("select a from Article a where a.user.userId = ?1 order by a.writtenDate desc")
    List<Article> findArticlesByUser(String user_id);

    @Query("select a.articleSeq from Article a where a.articleText like %?1%")
    List<Long> findArticlesByArticleText(String articleText);

    Article findByArticleSeq(Long articleSeq);


    @Query("SELECT u, a FROM Users u LEFT JOIN Article a ON a.user =:user")
    List<Article[]> getArticlesByUser(@Param("user") Users user);

    // 피드에 들어가는 글번호 최신순으로 가져오기
    // 내가 팔로우한 유저의 전체공개 글 + 맞팔인 유저의 맞팔공개 글 + 내 글
    @Query("SELECT a.articleSeq FROM Article a WHERE a.user.userSeq = ?1 OR a.user.userSeq IN (SELECT f.followingUserSeq FROM Following f WHERE f.user.userSeq = ?1) AND a.status = 1 OR a.user.userSeq IN (SELECT f1.followingUserSeq FROM Following f1 JOIN Following f2 ON f1.followingUserSeq = f2.user.userSeq WHERE f1.user.userSeq=?1 AND f2.followingUserSeq = ?1) AND a.status = 2 ORDER BY a.writtenDate DESC")
    //@Query(value = "SELECT a.article_Seq FROM Article a WHERE a.user_Seq = ?1 OR a.user_Seq IN ( SELECT f.following_user_Seq FROM Following f WHERE f.user_Seq = ?1) AND a.status = 1 OR a.user_seq IN ( SELECT f1.following_user_seq FROM Following f1 JOIN Following f2 ON f1.following_user_Seq = f2.user_seq WHERE f1.user_Seq = ?1 AND f2.following_user_seq = ?1 ) AND a.status = 2 ORDER BY a.written_date DESC", nativeQuery = true)
    List<Long> getArticleSeqListInFeed(Long userSeq);
}
