package com.finalproject.mvc.sobeit.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class Article {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long article_seq;
    @ManyToOne
    @JoinColumn(name = "user_seq", referencedColumnName = "user_seq")
    private Users user_seq;
    @Column(nullable = false)
    private int status;
    private String imageUrl;
    @Column(nullable = false)
    private String expenditure_category;
    @Column(nullable = false)
    private Long amount;
    private String financial_text;
    @Column(nullable = false)
    private String article_text;
    @Column(nullable = false)
    private LocalDateTime written_date;
    private LocalDateTime edited_date;

    @Column(nullable = false)
    private String article_type;
    private LocalDate consumption_date;

    @Column(nullable = false)
    private String is_allowed;
}
