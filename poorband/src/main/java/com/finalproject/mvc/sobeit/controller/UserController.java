package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.dto.ResponseDTO;
import com.finalproject.mvc.sobeit.dto.UserDTO;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@RequestBody UserDTO userDTO) {
        try {
            // 요청을 이용해 저장할 사용자 만들기
            Users user = Users.builder()
                    .userId(userDTO.getUser_id()) // 사용자 아이디
                    .email(userDTO.getEmail()) // 사용자 이메일
                    .userName(userDTO.getUser_name()) // 사용자 이름
                    .password(userDTO.getPassword()) // 사용자 비밀번호
                    .nickname(userDTO.getNickname()) // 사용자 닉네임
                    .phoneNumber(userDTO.getPhone_number()) // 사용자 전화번호
                    .build();

            // 서비스를 이용해 리포지터리에 사용자 저장
            Users registeredUser = userService.create(user);
            UserDTO responseUserDTO = UserDTO.builder()
                    .user_seq(registeredUser.getUserSeq()) // 사용자 고유 번호
                    .user_id(registeredUser.getUserId()) // 사용자 아이디
                    .email(registeredUser.getEmail()) // 사용자 이메일
                    .introduction(registeredUser.getIntroduction()) // 사용자 한 줄 소개
                    .user_name(registeredUser.getUserName()) // 사용자 이름
                    .nickname(registeredUser.getNickname()) // 사용자 닉네임
                    .user_tier(userDTO.getUser_tier()) // 사용자 티어
                    .challenge_count(userDTO.getChallenge_count()) // 사용자가 완료한 도전 과제 개수
                    .phone_number(userDTO.getPhone_number()) // 사용자 전화번호
                    .profile_image_url(userDTO.getProfile_image_url()) // 사용자 프로필 이미지 URL
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);
        }
        catch (Exception e) {
            // 사용자 정보는 항상 하나이므로 리스트로 만들어야 하는 ResponseDTO를 사용하지 않고 그냥 UserDTO 리턴
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();

            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticate(@RequestBody UserDTO userDTO) {
        Users user = userService.getByCredentials(
                userDTO.getUser_id(),
                userDTO.getPassword());

        if(user != null) {
            final UserDTO responseUserDTO = UserDTO.builder()
                    .user_seq(user.getUserSeq())
                    .user_id(user.getUserId())
                    .email(user.getEmail())
                    .introduction(user.getIntroduction())
                    .user_name(user.getUserName())
                    .nickname(user.getNickname())
                    .user_tier(user.getUserTier())
                    .challenge_count(user.getChallengeCount())
                    .phone_number(user.getPhoneNumber())
                    .profile_image_url(user.getProfileImageUrl())
                    .build();

            return ResponseEntity.ok().body(responseUserDTO);
        }
        else {
            ResponseDTO responseDTO = ResponseDTO.builder()
                    .error("Login failed.")
                    .build();

            return ResponseEntity
                    .internalServerError()
                    .body(responseDTO);
        }
    }
}
