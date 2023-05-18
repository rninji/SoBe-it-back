package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.dto.ReplyDTO;
import com.finalproject.mvc.sobeit.dto.ResponseDTO;
import com.finalproject.mvc.sobeit.entity.Reply;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.service.ReplyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/comment")
public class ReplyController {
    private final ReplyService replyService;

    /**
     * 댓글 작성
     * @param user
     * @param replyDTO
     * @return
     */
    @PostMapping("/write")
    public ResponseEntity<?> writeReply(@AuthenticationPrincipal Users user, @RequestBody ReplyDTO replyDTO) {
        try {
            Reply reply = Reply.builder()
                    .replyText(replyDTO.getReply_text())
                    .parentReplySeq(replyDTO.getParent_reply_seq())
                    .isUpdated(replyDTO.getIs_updated())
                    .build();

            Reply writtenReply = replyService.writeReply(user, replyDTO.getArticle_seq(), reply);
            ReplyDTO responseReplyDTO = ReplyDTO.builder()
                    .reply_seq(writtenReply.getReplySeq())
                    .article_seq(writtenReply.getArticle().getArticleSeq())
                    .user_seq(writtenReply.getUser().getUserSeq())
                    .reply_text(writtenReply.getReplyText())
                    .parent_reply_seq(writtenReply.getParentReplySeq())
                    .written_date(writtenReply.getWrittenDate())
                    .is_updated(writtenReply.getIsUpdated())
                    .build();

            return ResponseEntity.ok().body(responseReplyDTO);
        }
        catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }
    }

    /**
     * 댓글 수정
     * @param reply
     * @return
     */
    @RequestMapping("/update")
    public Reply updateReply(Reply reply){
        Reply updatedReply = replyService.updateReply(reply);
        if (updatedReply == null) {
            //throws new Exception("댓글 작성 실패");
        }
        return updatedReply;
    }

    /**
     * 댓글 삭제
     * @param replySeq
     */
    @RequestMapping("/delete")
    public void deleteReply(Long replySeq){
        replyService.deleteReply(replySeq);
    }
}
