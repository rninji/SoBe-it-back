package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.FollowNotificationDTO;
import com.finalproject.mvc.sobeit.dto.NotificationDTO;
import com.finalproject.mvc.sobeit.entity.FollowNotification;
import com.finalproject.mvc.sobeit.entity.LikeNotification;
import com.finalproject.mvc.sobeit.entity.ReplyNotification;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.repository.LikeNotificationRepo;
import com.finalproject.mvc.sobeit.repository.ReplyNotificationRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.util.List;
@Service
@RequiredArgsConstructor
@Transactional
public class NotificationServiceImpl implements NotificationService {
    private final LikeNotificationRepo likeNotificationRepo;
    private final ReplyNotificationRepo replyNotificationRepo;

    @Override
    public List<NotificationDTO> getAllNotification(Users user) {
        List<NotificationDTO> notifications;
        return null;
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

            } else if (type == 3) {

            } else if (type == 4) {
                likeNotificationRepo.deleteById(notificationSeq);
            }
        }
    }

    @Override
    public void deleteAllNotice(Users user) {

    }
    public NotificationDTO toFollowNotificationDto(FollowNotification followNotification) {
        NotificationDTO notificationDTO = FollowNotificationDTO.builder()
                .followingUserId(followNotification.getFromUser().getUserId())
                .followingUserNickName(followNotification.getFromUser().getNickname())
                .content(followNotification.getFromUser().getUserId() + "님이 회원님을 팔로우 합니다.")
                .notificationSeq(followNotification.getLikeNotificationSeq())
                .imageUrl(followNotification.getFromUser().getProfileImageUrl())
                .timestamp(followNotification.getNotificationDateTime())

                .build();

        return notificationDTO;
    }
    public NotificationDTO toLikeNotificationDto(LikeNotification likeNotification){

    }

    public NotificationDTO toReplyNotificationDto(ReplyNotification replyNotification) {

    }
}
