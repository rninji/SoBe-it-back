package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.GoalAmountDTO;
import com.finalproject.mvc.sobeit.entity.GoalAmount;
import com.finalproject.mvc.sobeit.entity.Users;

public interface GoalAmountService {
    /**
     * 도전과제 작성
     * @param user
     * @param goalAmountDTO
     * @return 작성된 도전과제
     **/
    public GoalAmount insertGoalAmount(Users user, GoalAmountDTO goalAmountDTO);

    /**
     * 도전과제 삭제
     * @param user
     * @param goalAmountSeq
     */
    public void deleteGoalAmount(Users user, Long goalAmountSeq);
}
