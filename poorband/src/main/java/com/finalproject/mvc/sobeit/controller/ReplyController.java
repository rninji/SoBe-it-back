package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.entity.Reply;
import com.finalproject.mvc.sobeit.service.ReplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ReplyController {
    @Autowired
    ReplyService replyService;

    /**
     * 댓글 작성
     * @param reply
     * @return
     */
    @RequestMapping("/comment/write")
    public Reply writeReply(Reply reply){
        Reply writtenReply = replyService.writeReply(reply);
        if (writtenReply == null) {
            //throws new Exception("댓글 작성 실패");
        }
        return writtenReply;
    }

    /**
     * 댓글 수정
     * @param reply
     * @return
     */
    @RequestMapping("/comment/update")
    public Reply updateReply(Reply reply){
        Reply updatedReply = replyService.updateReply(reply);
        if (updatedReply == null) {
            //throws new Exception("댓글 작성 실패");
        }
        return updatedReply;
    }

    /**
     * 댓글 삭제
     * @param id
     */
    @RequestMapping("/comment/delete")
    public void deleteReply(Long id){
        replyService.deleteReply(id);
    }

}
