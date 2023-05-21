package com.finalproject.mvc.sobeit.controller;

import com.finalproject.mvc.sobeit.dto.GoalAmountCntDTO;
import com.finalproject.mvc.sobeit.dto.GoalAmountDTO;
import com.finalproject.mvc.sobeit.dto.ResponseDTO;
import com.finalproject.mvc.sobeit.entity.GoalAmount;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.service.GoalAmountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/profile/challenge")
public class GoalAmountController {
    private final GoalAmountService goalAmountService;

    /**
     * 도전과제 정보 가져오기(성공한 도전과제 갯수,도전과제 갯수)
     * @param userIdMap
     * @return 성공한 도전과제 갯수&도전과제 갯수
     **/
    @PostMapping("/cnt")
    public ResponseEntity<?> selectGoalAmountSuccessCnt(@RequestBody Map<String, String> userIdMap) {
        try {
            GoalAmountCntDTO dto = goalAmountService.goalAmountCnt(userIdMap.get("userId"));
            return ResponseEntity.ok().body(dto);
        } catch(Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }
    }

    /**
     * 도전 과제 정보 가져오기
     * @param userIdMap
     * @return 도전 과제 목록
     * */
    @RequestMapping("")
    public ResponseEntity<?> selectGoalAmount(@RequestBody Map<String, String> userIdMap) {
        try {
            List<GoalAmount> list = goalAmountService.selectGoalAmount(userIdMap.get("userId"));
            return ResponseEntity.ok().body(list);
        } catch(Exception e) {
            ResponseDTO responseDTO = ResponseDTO.builder().error(e.getMessage()).build();
            return ResponseEntity
                    .internalServerError() // Error 500
                    .body(responseDTO);
        }
    }

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
