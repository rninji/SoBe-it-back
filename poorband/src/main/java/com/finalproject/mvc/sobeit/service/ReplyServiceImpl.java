package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.Reply;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.repository.ArticleRepo;
import com.finalproject.mvc.sobeit.repository.ReplyRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ArticleRepo articleRepo;
    private final ReplyRepo replyRepo;

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

        return replyRepo.save(reply);
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
