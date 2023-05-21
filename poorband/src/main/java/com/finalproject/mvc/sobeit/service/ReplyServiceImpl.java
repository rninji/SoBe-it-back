package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.ReplyDTO;
import com.finalproject.mvc.sobeit.dto.ReplyLikeDTO;
import com.finalproject.mvc.sobeit.dto.UserDTO;
import com.finalproject.mvc.sobeit.entity.*;
import com.finalproject.mvc.sobeit.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class ReplyServiceImpl implements ReplyService {
    private final ArticleRepo articleRepo;
    private final ReplyRepo replyRepo;
    private final ReplyLikeRepo replyLikeRepo;
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

        /**
         * 댓글 쓴 유저랑 게시글을 쓴 유저가 같은 유저가 아닐때만 알림 생성
         */
        if (!Objects.equals(user.getUserSeq(), replyArticle.getUser().getUserSeq())){
            Users userToSendNotification = replyArticle.getUser(); // 알림 등록할 유저
            String url = "http://localhost:3000/article/detail/" + replyArticle.getArticleSeq();
            ReplyNotification replyNotification = ReplyNotification.builder().user(userToSendNotification)
                    .reply(reply)
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

        Reply updatingReply = replyRepo.findByReplySeq(reply.getReplySeq()); // 업데이트할 댓글

        updatingReply.setReplyText(reply.getReplyText()); // 댓글 수정 내용 반영
        updatingReply.setIsUpdated(updatingReply.getIsUpdated() + 1); // 수정 횟수 추가

        return replyRepo.save(updatingReply);
    }

    /**
     * 댓글 삭제
     * @param user
     * @param replyDTO
     */
    @Override
    public ReplyDTO deleteReply(final Users user, ReplyDTO replyDTO){
        if (user == null || replyDTO == null) {
            throw new RuntimeException("Invalid arguments");
        }

        Long deletingReplySeq = replyDTO.getReply_seq();

        if (replyRepo.existsByReplySeqAndUser(deletingReplySeq, user)) {
            Reply deletingReply = replyRepo.findByReplySeqAndUser(deletingReplySeq, user); // 삭제하려는 댓글

            deletingReply.setReplyText("삭제된 댓글입니다.");
            deletingReply.setIsUpdated(-1);

            Reply deletedReply = replyRepo.save(deletingReply);

            ReplyDTO responseReplyDTO = ReplyDTO.builder()
                    .reply_seq(deletedReply.getReplySeq())
                    .reply_text(deletedReply.getReplyText())
                    .is_updated(deletedReply.getIsUpdated())
                    .build();

            return responseReplyDTO;
        }
        else {
            return null;
        }
    }

    /**
     * 해당 글의 댓글 전체 조회
     * @param articleSeq
     * @return
     */
    @Override
    public List<ReplyDTO> selectAllReply(Long articleSeq) {
        List<Reply> writtenReplyList = replyRepo.findReplyByArticleSeq(articleSeq);

        List<ReplyDTO> responseReplyDTOList = new ArrayList<>();
        for (Reply writtenReply : writtenReplyList) {
            int replyLikeCount = countReplyLike(writtenReply.getReplySeq());
            UserDTO replyWriter = selectReplyWriter(writtenReply.getUser().getUserSeq());

            responseReplyDTOList.add(
                    ReplyDTO.builder()
                            .reply_seq(writtenReply.getReplySeq())
                            .reply_text(writtenReply.getReplyText())
                            .parent_reply_seq(writtenReply.getParentReplySeq())
                            .written_date(writtenReply.getWrittenDate())
                            .reply_like_cnt(replyLikeCount)
                            .nickname(replyWriter.getNickname())
                            .profile_image_url(replyWriter.getProfile_image_url())
                            .build()
            );
        }

        return responseReplyDTOList;
    }

    /**
     * 댓글 작성자 찾기
     * @param userSeq
     * @return
     */
    public UserDTO selectReplyWriter(Long userSeq) {
        Users writer = replyRepo.findReplyUsersByUserSeq(userSeq);

        UserDTO responseUserDTO = UserDTO.builder()
                .user_id(writer.getUserId())
                .nickname(writer.getNickname())
                .profile_image_url(writer.getProfileImageUrl())
                .build();

        return responseUserDTO;
    }

    /**
     * 댓글 좋아요
     * @param user
     * @param replyLikeDTO
     * @return
     */
    @Override
    public ReplyLikeDTO likeReply(final Users user, ReplyLikeDTO replyLikeDTO) {
        if (user == null || replyLikeDTO == null) {
            throw new RuntimeException("Invalid arguments");
        }

        if (!replyLikeDTO.getIs_liked()) { // 댓글 좋아요가 눌려 있지 않은 경우, 댓글 좋아요 생성
            ReplyLike replyLike = ReplyLike.builder()
                    .reply(replyRepo.findByReplySeq(replyLikeDTO.getReply_seq()))
                    .user(userRepo.findByUserSeq(user.getUserSeq()))
                    .build();

            replyLikeRepo.save(replyLike);

            ReplyLikeDTO responseReplyLikeDTO = ReplyLikeDTO.builder()
                    .reply_like_seq(replyLike.getReplyLikeSeq())
                    .reply_seq(replyLike.getReply().getReplySeq())
                    .user_seq(replyLike.getUser().getUserSeq())
                    .build();

            return responseReplyLikeDTO;
        }
        else { // 댓글 좋아요가 눌려 있는 경우, 댓글 좋아요 삭제
            Reply reply = replyRepo.findByReplySeq(replyLikeDTO.getReply_seq());

            ReplyLike existingReplyLike = replyLikeRepo.findByReplyAndUser(reply, user); // 현재 로그인한 사용자와 좋아요를 누른 사용자가 같은 댓글 좋아요

            replyLikeRepo.delete(existingReplyLike);

            ReplyLikeDTO responseReplyLikeDTO = ReplyLikeDTO.builder()
                    .reply_seq(replyLikeDTO.getReply_seq())
                    .user_seq(user.getUserSeq())
                    .build();

            return responseReplyLikeDTO;
        }
    }

    /**
     * 댓글 좋아요 개수 확인
     * @param replySeq
     * @return
     */
    public int countReplyLike(Long replySeq) {
        return replyLikeRepo.findCountReplyLikeByReplySeq(replySeq);
    }
}
