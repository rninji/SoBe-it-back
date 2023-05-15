package com.finalproject.mvc.sobeit.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Vote {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long vote_seq;

    @ManyToOne
    @JoinColumn(name = "article_seq", referencedColumnName = "article_seq")
    private Article article_seq;

    @Column(nullable = false)
    private int vote;
}
