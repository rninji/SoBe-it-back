package com.finalproject.mvc.sobeit.repository;

import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.Users;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ArticleRepo extends JpaRepository<Article, Long> {
    @Query("select a from Article a where a.user.userId = ?1 order by a.writtenDate desc")
    List<Article> findArticlesByUser(String user_id);

    @Query("select a.articleSeq from Article a where a.articleText like %?1%")
    List<Long> findArticlesByArticleText(String articleText);

    Article findByArticleSeq(Long articleSeq);

    @Query("select a from Article a where a.user.userSeq in : userSeq AND a.status = 1")
    List<Article> findByUserSeqsAndStatus(List<Long> userSeq);

    @Query("select a from Article a where a.user.userSeq in : userSeq AND a.status = 2")
    List<Article> findByMutualUserSeq(List<Long> userSeq);

    @Query("SELECT u, a FROM Users u LEFT JOIN Article a ON a.user =:user")
    List<Article[]> getArticlesByUser(@Param("user") Users user);

    // 피드에 들어가는 글번호 최신순으로 가져오기
    // 내가 팔로우한 유저의 전체공개 글 + 맞팔인 유저의 맞팔공개 글 + 내 글
    @Query("SELECT a.articleSeq FROM Article a WHERE (a.user.userSeq = :userSeq OR a.user.userSeq IN (SELECT f.followingUserSeq FROM Following f WHERE f.user.userSeq = :userSeq) AND a.status = 1 OR a.user.userSeq IN (SELECT f1.followingUserSeq FROM Following f1 JOIN Following f2 ON f1.followingUserSeq = f2.user.userSeq WHERE f1.user.userSeq=:userSeq AND f2.followingUserSeq = :userSeq) AND a.status = 2) AND a.articleSeq < :lastArticleSeq ORDER BY a.writtenDate DESC")
    Page<Long> findArticleSeqListInFeed(@Param("userSeq") Long userSeq, @Param("lastArticleSeq") Long lastArticleSeq, Pageable pageable);

    @Query("SELECT a.articleSeq FROM Article a WHERE a.user.userSeq = ?1 OR a.user.userSeq IN (SELECT f.followingUserSeq FROM Following f WHERE f.user.userSeq = ?1) AND a.status = 1 OR a.user.userSeq IN (SELECT f1.followingUserSeq FROM Following f1 JOIN Following f2 ON f1.followingUserSeq = f2.user.userSeq WHERE f1.user.userSeq=?1 AND f2.followingUserSeq = ?1) AND a.status = 2 ORDER BY a.writtenDate DESC")
    Page<Long> findArticleSeqListInFeedFirst(Long userSeq, Pageable pageable);


    // 유저가 해당 날짜에 쓴 지출 글 전부 가져오기
    @Query("SELECT a FROM Article a WHERE a.user.userSeq=?1 AND a.consumptionDate=?2 AND a.articleType=1")
    List<Article> findExpenditureArticlesByConsumptionDate(Long userSeq, LocalDate date);

    // 유저가 해당 날짜에 쓴 지출 금액 합계
    @Query("SELECT SUM(a.amount) FROM Article a WHERE a.user.userSeq=?1 AND a.consumptionDate=?2 AND a.articleType=1")
    Long findSumAmountByConsumptionDate(Long userSeq, LocalDate date);

    //유저가 해당 달에 쓴 지출 내역의 가격 합계
    @Query ("SELECT SUM(a.amount) FROM Article a WHERE a.user.userSeq=?1 AND a.articleType=1 AND a.consumptionDate>=?2 AND a.consumptionDate<?3")
    Long findSumAmountByUserSeqAndDate(Long userSeq, LocalDate start, LocalDate end);

    // 유저가 해당 달에 쓴 특정 카테고리 지출 내역의 가격 합계
    @Query("SELECT SUM(a.amount) FROM Article a WHERE a.user.userSeq=?1 AND a.articleType=1 AND a.expenditureCategory=?2 AND a.consumptionDate>=?3 AND a.consumptionDate<?4")
    Long findSumAmountByUserSeqAndCategory(Long userSeq, int category, LocalDate start, LocalDate end);
}
