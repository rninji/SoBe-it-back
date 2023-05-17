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
    private Long articleSeq;

    @ManyToOne
    @JoinColumn(name = "userSeq", referencedColumnName = "userSeq", nullable = false)
    private Users user;

    @Column(nullable = false)
    private int status;

    private String imageUrl;

    @Column(nullable = false)
    private String expenditureCategory;

    @Column(nullable = false)
    private Long amount;

    private String financialText;

    @Column(nullable = false)
    private String articleText;

    @Column(nullable = false)
    private LocalDateTime writtenDate;

    private LocalDateTime editedDate;

    @Column(nullable = false)
    private String articleType;
    @Column(nullable = false)
    private LocalDate consumptionDate;

    @Column(nullable = false)
    private String isAllowed;
}
