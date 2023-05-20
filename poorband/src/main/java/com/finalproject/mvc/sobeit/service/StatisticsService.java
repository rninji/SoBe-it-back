package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.ExpenditureResponseDTO;
import com.finalproject.mvc.sobeit.entity.Users;

import java.util.List;

public interface StatisticsService {
    /**
     * 월별 지출 내역
     */
    public List<ExpenditureResponseDTO>[] getExpenditure(Users user, int year, int month);

}
