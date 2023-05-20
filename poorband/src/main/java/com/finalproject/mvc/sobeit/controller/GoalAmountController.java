package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.dto.GoalAmountDTO;
import com.finalproject.mvc.sobeit.dto.ResponseDTO;
import com.finalproject.mvc.sobeit.entity.GoalAmount;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.service.GoalAmountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/challenge")
public class GoalAmountController {
    private final GoalAmountService goalAmountService;

    /**
     * 도전과제 작성
     * @param user
     * @param goalAmountDTO
     * @return 성공 시 작성된 도전과제
     */
    @RequestMapping("/add")
    public ResponseEntity<?> insertGoalAmount(@AuthenticationPrincipal Users user, @RequestBody GoalAmountDTO goalAmountDTO){
        try {
            GoalAmount goalAmount = goalAmountService.insertGoalAmount(user, goalAmountDTO);
            return ResponseEntity.ok().body(goalAmount);
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }
    }

    /**
     * 도전과제 삭제
     * @param user
     * @param { "goalAmountSeq": 삭제할 글번호}
     * @return
     */
    @RequestMapping("/delete")
    public ResponseEntity<?> deleteGoalAmount(@AuthenticationPrincipal Users user, @RequestBody Map<String, Long> goalAmountMap){
        try {
            goalAmountService.deleteGoalAmount(user, goalAmountMap.get("goalAmountSeq"));
            return ResponseEntity.ok().body("delete success");
        } catch (Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }
    }
}
