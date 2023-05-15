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
    private Long notice_seq;
    @ManyToOne
    @JoinColumn(name = "user_seq", referencedColumnName = "user_seq")
    private Users user_seq;
    @ManyToOne
    @JoinColumn(name = "from_user_seq", referencedColumnName = "user_seq")
    private Users from_user_seq;
    @Column(nullable = false)
    private String type;
    @ManyToOne
    @JoinColumn(name = "not_content_seq", referencedColumnName = "article_seq")
    private Article not_content_seq;
    @Column(nullable = false)
    private DATE not_datetime;
}
