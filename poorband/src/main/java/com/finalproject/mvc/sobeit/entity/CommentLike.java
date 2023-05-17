package com.finalproject.mvc.sobeit.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class CommentLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long replyLikeSeq;

    @ManyToOne
    @JoinColumn(name = "replySeq", referencedColumnName = "replySeq", nullable = false)
    private Reply reply;

    @ManyToOne
    @JoinColumn(name = "userSeq", referencedColumnName = "userSeq", nullable = false)
    private Users user;
}
