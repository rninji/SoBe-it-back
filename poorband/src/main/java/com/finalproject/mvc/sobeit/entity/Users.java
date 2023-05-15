package com.finalproject.mvc.sobeit.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Users {
    @Id
    @Column (name = "user_seq", nullable = false)
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long user_seq; // 사용자 고유 번호

    @Column (nullable = false)
    private String user_id; // 사용자 아이디

    @Column (nullable = false)
    private String email; // 사용자 이메일

    private String introduction; // 사용자 한 줄 소개

    @Column (nullable = false)
    private String user_name; //사용자 이름

    @Column (nullable = false)
    private String password; // 사용자 비밀번호

    @Column (nullable = false)
    private String nickname; // 사용자 닉네임

    @Column(nullable = false)
    private String user_tier; // 사용자 티어

    @Column(nullable = false)
    private Long challenge_count; // 사용자가 완료한 도전 과제 개수

    @Column(nullable = false, length = 20)
    private String phone_number; // 사용자 전화번호

    private String profile_image_url; // 사용자 프로필 이미지 링크

    @PrePersist
    public void prePersist(){
        this.user_tier = this.user_tier == null ? "BRONZE" : this.user_tier;
        this.challenge_count = this.challenge_count == null ? 0 : this.challenge_count;
    }
}
