package com.finalproject.mvc.sobeit.entity;

import lombok.*;
import oracle.sql.DATE;

import javax.persistence.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Notice {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long noticeSeq;

    @ManyToOne
    @JoinColumn(name = "userSeq", referencedColumnName = "userSeq", nullable = false)
    private Users user;

    @ManyToOne
    @JoinColumn(name = "fromUserSeq", referencedColumnName = "userSeq", nullable = false)
    private Users fromUser;

    @Column(nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "notContentSeq", referencedColumnName = "articleSeq")
    private Article notContent;

    @Column(nullable = false)
    private DATE notDatetime;
}
