package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.FollowNotificationDTO;
import com.finalproject.mvc.sobeit.dto.LikeNotificationDTO;
import com.finalproject.mvc.sobeit.dto.NotificationDTO;
import com.finalproject.mvc.sobeit.dto.ReplyNotificationDTO;
import com.finalproject.mvc.sobeit.entity.FollowNotification;
import com.finalproject.mvc.sobeit.entity.LikeNotification;
import com.finalproject.mvc.sobeit.entity.ReplyNotification;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.repository.FollowNotificationRepo;
import com.finalproject.mvc.sobeit.repository.LikeNotificationRepo;
import com.finalproject.mvc.sobeit.repository.ReplyNotificationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {
    private final LikeNotificationRepo likeNotificationRepo; // 게시글 좋아요
    private final ReplyNotificationRepo replyNotificationRepo; // 댓글 알림
    private final FollowNotificationRepo followNotificationRepo; // 팔로우 알림
    private final ArticleServiceImpl articleService;


    @Override
    public List<NotificationDTO> getAllNotification(Users user) throws Exception {
        List<NotificationDTO> notifications = new ArrayList<>();
        List<FollowNotification> followNotificationList = followNotificationRepo.findByUser(user).orElse(null);

        if (followNotificationList != null) {
            for (FollowNotification followNotification : followNotificationList) {
                // 맞팔 체크 로직 필요
                boolean followToFollowCheck = articleService.followToFollowCheck(user.getUserSeq(), followNotification.getFromUser().getUserSeq());
                notifications.add(toFollowNotificationDto(followNotification, followToFollowCheck));
            }
        }

        List<LikeNotification> likeNotificationList = likeNotificationRepo.findByUser(user).orElse(null);

        if (likeNotificationList != null) {
            for (LikeNotification likeNotification : likeNotificationList) {
                notifications.add(toLikeNotificationDto(likeNotification));
            }
        }

        //reply알림
        List<ReplyNotification> replyNotificationList = replyNotificationRepo.findByUser(user).orElse(null);
        if (replyNotificationList != null) {
            for (ReplyNotification replyNotification : replyNotificationList) {
                notifications.add(toReplyNotificationDto(replyNotification));
            }
        }

        //댓글 좋아요 알림



        if (notifications.size() == 0) throw new Exception("알림이 없습니다.");
        return notifications;
    }

    /**\
     *
     * @param type : 1=댓글알림, 2=팔로우알림, 3=댓글좋아요, 4=게시글좋아요
     */
    @Override
    public void deleteOneNotice(Users user, Long notificationSeq, int type) throws Exception {
        if (!replyNotificationRepo.existsById(notificationSeq)) {
            throw new Exception("존재하지 않는 알림입니다");
        }
        else{
            if (type == 1){
                replyNotificationRepo.deleteById(notificationSeq);
            } else if (type == 2) {
                followNotificationRepo.deleteById(notificationSeq);
            } else if (type == 3) {

            } else if (type == 4) {
                likeNotificationRepo.deleteById(notificationSeq);
            }
        }
    }

    /**
     *
     * @param user : 접속한 유저
     * @return : 삭제 성공 여부
     */
    @Override
    public boolean deleteAllNotice(Users user) throws Exception {
        // 4개의 알림 Repo에 전부 삭제쿼리 날려주기.

        return true;
    }

    /**
     *
     * @param followNotification : 엔티티
     * @param isFollowed : 맞팔상태인지?
     * @return : 알림 DTO
     */
    public NotificationDTO toFollowNotificationDto(FollowNotification followNotification, boolean isFollowed) {
        NotificationDTO notificationDTO = FollowNotificationDTO.builder()
                    .followingUserId(followNotification.getFromUser().getUserId())
                    .followingUserNickName(followNotification.getFromUser().getNickname())
                    .content(followNotification.getFromUser().getUserId() + "님이 회원님을 팔로우 합니다.")
                    .notificationSeq(followNotification.getLikeNotificationSeq())
                    .isFollowing(isFollowed)
                    .imageUrl(followNotification.getFromUser().getProfileImageUrl())
                    .timestamp(followNotification.getNotificationDateTime())
                    .type(2)
                    .build();

        return notificationDTO;
    }
    public NotificationDTO toLikeNotificationDto(LikeNotification likeNotification){
        NotificationDTO notificationDTO = null;
        if (likeNotification.getType() == 1) {
            notificationDTO = LikeNotificationDTO.builder()
                    .notificationSeq(likeNotification.getLikeNotificationSeq())
                    .timestamp(likeNotification.getNotificationDateTime())
                    //.imageUrl() 좋아요 하트 이미지 넣어줘야함
                    .articleContent(likeNotification.getNotArticleSeq().getArticleText())
                    .content("게시글의 좋아요 수가 10개 이상입니다.")
                    .url(likeNotification.getUrl())
                    .type(4)
                    .build();
        } else if (likeNotification.getType() == 2) {
            notificationDTO = LikeNotificationDTO.builder()
                    .notificationSeq(likeNotification.getLikeNotificationSeq())
                    .timestamp(likeNotification.getNotificationDateTime())
                    //.imageUrl() 좋아요 하트 이미지 넣어줘야함
                    .articleContent(likeNotification.getNotArticleSeq().getArticleText())
                    .content("게시글의 좋아요 수가 50개 이상입니다.")
                    .url(likeNotification.getUrl())
                    .type(4)
                    .build();
        } else if (likeNotification.getType() == 3) {
            notificationDTO = LikeNotificationDTO.builder()
                    .notificationSeq(likeNotification.getLikeNotificationSeq())
                    .timestamp(likeNotification.getNotificationDateTime())
                    //.imageUrl() 좋아요 하트 이미지 넣어줘야함
                    .articleContent(likeNotification.getNotArticleSeq().getArticleText())
                    .content("게시글의 좋아요 수가 100개 이상입니다.")
                    .url(likeNotification.getUrl())
                    .type(4)
                    .build();
        }

        return notificationDTO;
    }

    public NotificationDTO toReplyNotificationDto(ReplyNotification replyNotification) {
        NotificationDTO notificationDTO = ReplyNotificationDTO.builder()
                .notificationSeq(replyNotification.getReplyNotificationSeq())
                .content("새로운 댓글이 달렸습니다. : " + replyNotification.getReply().getReplyText())
                .articleContent(replyNotification.getArticle().getArticleText())
                .timestamp(replyNotification.getNotificationDateTime())
                .imageUrl(replyNotification.getReply().getUser().getProfileImageUrl())
                .url(replyNotification.getUrl())
                .type(1)
                .build();

        return notificationDTO;
    }
}
