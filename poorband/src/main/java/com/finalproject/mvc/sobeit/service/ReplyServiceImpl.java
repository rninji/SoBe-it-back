package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.Reply;
import com.finalproject.mvc.sobeit.entity.ReplyNotification;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.repository.ReplyNotificationRepo;
import com.finalproject.mvc.sobeit.repository.ArticleRepo;
import com.finalproject.mvc.sobeit.repository.ReplyRepo;
import com.finalproject.mvc.sobeit.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService {
    private final ArticleRepo articleRepo;
    private final ReplyRepo replyRepo;
    private final UserRepo userRepo;
    private final ReplyNotificationRepo replyNotificationRepo;

    /**
     * 댓글 작성
     * @param user,
     * @param articleNum
     * @param reply
     */
    @Override
    public Reply writeReply(final Users user, Long articleNum, Reply reply){
        if (reply == null || user == null || articleNum == null) {
            throw new RuntimeException("Invalid arguments");
        }

        reply.setArticle(articleRepo.findByArticleSeq(articleNum));
        reply.setUser(user);
        reply.setWrittenDate(LocalDateTime.now());
        Reply savedReply = replyRepo.save(reply);

        /**
         * 댓글을 작성함에 따라 해당 글을 작성한 User의 seq을 기준으로 notificaiton 엔티티 저장
         */
        Article replyArticle = reply.getArticle(); // 댓글을 달 Article

        if (!Objects.equals(user.getUserSeq(), replyArticle.getUser().getUserSeq())){
            Users userToSendNotification = replyArticle.getUser(); // 알림 등록할 유저
            String url = "http://localhost:3000/article/detail/" + replyArticle.getArticleSeq();
            ReplyNotification replyNotification = ReplyNotification.builder().user(userToSendNotification)
                    .article(replyArticle)
                    .notificationDateTime(LocalDateTime.now())
                    .url(url).build();
            replyNotificationRepo.save(replyNotification);
        }
        return savedReply;
    }

    /**
     * 댓글 수정
     * @param reply
     * @return
     */
    @Override
    public Reply updateReply(Reply reply){
        if (reply == null) {
            throw new RuntimeException("Invalid arguments");
        }

        Reply updatingReply = replyRepo.findReplyByReplySeq(reply.getReplySeq()); // 업데이트할 댓글

        updatingReply.setReplyText(reply.getReplyText()); // 댓글 수정 내용 반영
        updatingReply.setIsUpdated(updatingReply.getIsUpdated() + 1); // 수정 횟수 추가

        return replyRepo.save(updatingReply);
    }

    /**
     * 댓글 삭제
     * @param id
     */
    @Override
    public void deleteReply(Long id){
        replyRepo.deleteById(id);
        // 자식 댓글 다 삭제해야 댐ㅎㅎ
    }
}
