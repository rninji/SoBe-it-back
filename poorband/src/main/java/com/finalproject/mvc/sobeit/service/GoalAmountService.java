package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.GoalAmountCntDTO;
import com.finalproject.mvc.sobeit.dto.GoalAmountDTO;
import com.finalproject.mvc.sobeit.dto.GoalAmountResponseDTO;
import com.finalproject.mvc.sobeit.entity.GoalAmount;
import com.finalproject.mvc.sobeit.entity.Users;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

import java.util.List;

public interface GoalAmountService {
    /**
     * 도전과제 정보 가져오기(성공한 도전과제 갯수,도전과제 갯수)
     * @param userId
     * @return 성공한 도전과제 갯수&도전과제 갯수
     **/
    public GoalAmountCntDTO goalAmountCnt(String userId);

    /**
     * 도전과제 정보 가져오기(도전과제 리스트)
     * @param user
     * @return 도전과제 목록
     **/
    public List<GoalAmountResponseDTO> selectGoalAmount(Users user);

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
