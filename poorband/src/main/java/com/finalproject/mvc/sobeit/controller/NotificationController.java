package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.dto.NotificationDTO;
import com.finalproject.mvc.sobeit.dto.ResponseDTO;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/notification")
public class NotificationController {
    private final NotificationService notificationService;
    @PostMapping("/selectAll")
    public ResponseEntity<?> selectAllNotification(@AuthenticationPrincipal Users user) {
        try{
            List<NotificationDTO> notificationDTOList = notificationService.getAllNotification(user);
            return ResponseEntity.ok().body(notificationDTOList);
        }catch (Exception e){
            ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .internalServerError()
                    .body(responseDTO);
        }
    }

    @PostMapping("/deleteone")
    public ResponseEntity<?> deleteOneNotification(@AuthenticationPrincipal Users user, Long notificationSeq, int type) {
        try{
            notificationService.deleteOneNotice(user, notificationSeq, type);
            return ResponseEntity.ok().body(true);
        }catch (Exception e) {
            ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .internalServerError()
                    .body(responseDTO);
        }
    }
    @PostMapping("/deleteall")
    public ResponseEntity<?> deleteAllNotification(@AuthenticationPrincipal Users user) {
        try{
            notificationService.deleteAllNotice(user);
            return ResponseEntity.ok().body(true);
        }catch (Exception e) {
            ResponseDTO<Object> responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .internalServerError()
                    .body(responseDTO);
        }
    }
}
