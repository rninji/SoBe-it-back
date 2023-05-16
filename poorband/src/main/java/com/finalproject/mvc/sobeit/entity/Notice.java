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
    @JoinColumn(name = "userSeq", referencedColumnName = "userSeq")
    private Users userSeq;

    @ManyToOne
    @JoinColumn(name = "fromUserSeq", referencedColumnName = "userSeq")
    private Users fromUserSeq;

    @Column(nullable = false)
    private String type;

    @ManyToOne
    @JoinColumn(name = "notContentSeq", referencedColumnName = "articleSeq")
    private Article notContentSeq;

    @Column(nullable = false)
    private DATE notDatetime;
}
