package com.finalproject.mvc.sobeit.dto;

import com.finalproject.mvc.sobeit.entity.Users;
import lombok.*;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDTO {
    private Long articleSeq;
    private Users user;
    private int status;
    private String imageUrl;
    private String expenditureCategory;
    private Long amount;
    private String financialText;
    private String articleText;
    private String articleType;
    private LocalDate consumptionDate;
    private String isAllowed;
}
