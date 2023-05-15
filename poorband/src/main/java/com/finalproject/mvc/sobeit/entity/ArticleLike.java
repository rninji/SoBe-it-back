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
    private Long like_seq;
    @ManyToOne
    @JoinColumn(name = "article_seq", referencedColumnName = "article_seq")
    private Article article_seq;
    @ManyToOne
    @JoinColumn(name = "user_seq", referencedColumnName = "user_seq")
    private Users user_seq;
}
