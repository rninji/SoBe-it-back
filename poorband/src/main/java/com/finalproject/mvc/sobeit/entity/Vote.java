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
    private Long voteSeq;

    @ManyToOne
    @JoinColumn(name = "articleSeq", referencedColumnName = "articleSeq", nullable = false)
    private Article article;

    @Column(nullable = false)
    private int vote;

    @ManyToOne
    @JoinColumn(name = "userSeq", referencedColumnName = "userSeq", nullable = false)
    private Users user;
}
