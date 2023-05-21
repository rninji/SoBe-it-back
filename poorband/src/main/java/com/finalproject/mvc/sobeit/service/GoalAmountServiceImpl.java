package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.GoalAmountCntDTO;
import com.finalproject.mvc.sobeit.dto.GoalAmountDTO;
import com.finalproject.mvc.sobeit.dto.GoalAmountResponseDTO;
import com.finalproject.mvc.sobeit.entity.GoalAmount;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.repository.GoalAmountRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalAmountServiceImpl implements GoalAmountService{

    private final GoalAmountRepo goalAmountRep;

    @Override
    public GoalAmountCntDTO goalAmountCnt(String userId) {
        int successCnt = goalAmountRep.findGoalAmountSeqSuccess(userId).size();
        int AllCnt = goalAmountRep.findGoalAmountSeq(userId).size();

        GoalAmountCntDTO cntDTO = GoalAmountCntDTO.builder()
                .successGoalAmountCnt(successCnt)
                .goalAmountCnt(AllCnt)
                .build();

        return cntDTO;
    }

    @Override
    public List<GoalAmount> selectGoalAmount(String userId) {
       List<GoalAmount> goalAmountList = goalAmountRep.findGoalAmountByUserId(userId);
       if (goalAmountList == null) throw new RuntimeException("도전과제가 없습니다.");
        return goalAmountList;
    }

    @Override
    public GoalAmount insertGoalAmount(Users user, GoalAmountDTO goalAmountDTO) throws RuntimeException{
        // 요청 이용해 저장할 도전과제 생성
        GoalAmount goalAmount = GoalAmount.builder()
                .user(user)
                .goalAmount(goalAmountDTO.getGoalAmount())
                .title(goalAmountDTO.getTitle())
                .startDate(goalAmountDTO.getStartDate())
                .endDate(goalAmountDTO.getEndDate())
                .routine(goalAmountDTO.getRoutine())
                .isSuccess(goalAmountDTO.getIsSuccess())
                .build();
        return goalAmountRep.save(goalAmount);
    }

    @Override
    public void deleteGoalAmount(Users user, Long goalAmountSeq) throws RuntimeException{
        GoalAmount foundGoalAmount = goalAmountRep.findById(goalAmountSeq).orElse(null);
        if (foundGoalAmount == null){ // 삭제할 도전과제가 없는 경우
            throw new RuntimeException("삭제할 도전과제가 없습니다.");
        }

        if (user.getUserSeq() != foundGoalAmount.getUser().getUserSeq()){ // 삭제 요청 유저가 작성자가 아닐 경우 예외 발생
            throw new RuntimeException("작성자가 아닙니다.");
        }
        goalAmountRep.deleteById(goalAmountSeq);
    }
}
