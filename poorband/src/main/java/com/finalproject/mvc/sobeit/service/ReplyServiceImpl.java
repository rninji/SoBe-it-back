package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.Reply;
import com.finalproject.mvc.sobeit.entity.ReplyNotification;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.repository.ReplyNotificationRepo;
import com.finalproject.mvc.sobeit.repository.ReplyRepo;
import com.finalproject.mvc.sobeit.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepo replyRepo;
    private final UserRepo userRepo;
    private final ReplyNotificationRepo replyNotificationRepo;

    /**
     * 댓글 작성
     * @param reply
     */
    @Override
    public Reply writeReply(Reply reply){
        reply.setWrittenDate(LocalDateTime.now());
        Reply savedReply = replyRepo.save(reply);

        /**
         * 댓글을 작성함에 따라 해당 글을 작성한 User의 seq을 기준으로 notificaiton 엔티티 저장
         */
        Article replyArticle = reply.getArticle();
        Users userToSendNotification = replyArticle.getUser(); // 알림 등록할 유저
        String url = "http://localhost:3000/article/detail/" + replyArticle.getArticleSeq();
        ReplyNotification replyNotification = ReplyNotification.builder().user(userToSendNotification).article(replyArticle).notificationDateTime(LocalDateTime.now()).url(url).build();
        replyNotificationRepo.save(replyNotification);

        return savedReply;
    }

    /**
     * 댓글 수정
     * @param reply
     * @return
     */
    @Override
    public Reply updateReply(Reply reply){
        reply.setWrittenDate(LocalDateTime.now());
        // 수정일 갱신
        return replyRepo.save(reply);
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
