package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.Reply;
import com.finalproject.mvc.sobeit.repository.ReplyRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ReplyService {
    @Autowired
    ReplyRepo replyRepo;

    /**
     * 댓글 작성
     * @param reply
     */
    public Reply writeReply(Reply reply){
        reply.setWritten_date(LocalDateTime.now());
        return replyRepo.save(reply);
    }

    /**
     * 댓글 수정
     * @param reply
     * @return
     */
    public Reply updateReply(Reply reply){
        reply.setWritten_date(LocalDateTime.now());
        // 수정일 갱신
        return replyRepo.save(reply);
    }

    /**
     * 댓글 삭제
     * @param id
     */
    public void deleteReply(Long id){
        replyRepo.deleteById(id);
    }
}
