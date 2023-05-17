package com.finalproject.mvc.sobeit.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ReplyLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long replyLike;

    @ManyToOne
    @JoinColumn(name = "replySeq", referencedColumnName = "replySeq")
    private Reply reply;
    @ManyToOne
    @JoinColumn(name = "userSeq", referencedColumnName = "userSeq")
    private Users user;
}
