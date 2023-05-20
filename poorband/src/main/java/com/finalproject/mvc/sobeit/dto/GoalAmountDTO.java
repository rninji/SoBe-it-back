package com.finalproject.mvc.sobeit.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.finalproject.mvc.sobeit.entity.Users;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GoalAmountDTO {
    private Long goalAmountSeq;
    private Long goalAmount;
    private String title;
    private Users user;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime startDate;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime endDate;
    private String isSuccess;
    private String routine;

}
