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
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

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
     * @param user
     * @param replyDTO
     * @return
     */
    @PostMapping("/update")
    public ResponseEntity<?> updateReply(@AuthenticationPrincipal Users user, @RequestBody ReplyDTO replyDTO){
        if (Objects.equals(user.getUserSeq(), replyDTO.getUser_seq())) {
            Reply reply = Reply.builder()
                    .replySeq(replyDTO.getReply_seq())
                    .replyText(replyDTO.getReply_text())
                    .build();

            Reply updatedReply = replyService.updateReply(reply);
            ReplyDTO responseReplyDTO = ReplyDTO.builder()
                    .reply_seq(updatedReply.getReplySeq())
                    .article_seq(updatedReply.getArticle().getArticleSeq())
                    .user_seq(updatedReply.getUser().getUserSeq())
                    .reply_text(updatedReply.getReplyText())
                    .parent_reply_seq(updatedReply.getParentReplySeq())
                    .written_date(updatedReply.getWrittenDate())
                    .is_updated(updatedReply.getIsUpdated())
                    .build();

            return ResponseEntity.ok().body(responseReplyDTO);
        }
        else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Update failed.")
                    .build();

            return ResponseEntity
                    .internalServerError()
                    .body(responseDTO);
        }
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
