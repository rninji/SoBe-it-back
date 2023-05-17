package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.entity.Reply;
import com.finalproject.mvc.sobeit.repository.ReplyRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReplyServiceImpl implements ReplyService {
    private final ReplyRepo replyRepo;

    /**
     * 댓글 작성
     * @param reply
     */
    @Override
    public Reply writeReply(Reply reply){
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
