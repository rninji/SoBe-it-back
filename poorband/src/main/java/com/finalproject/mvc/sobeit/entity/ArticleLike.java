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
public class ArticleLike {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long likeSeq;

    @ManyToOne
    @JoinColumn(name = "articleSeq", referencedColumnName = "articleSeq")
    private Article article;

    @ManyToOne
    @JoinColumn(name = "userSeq", referencedColumnName = "userSeq")
    private Users user;
}
