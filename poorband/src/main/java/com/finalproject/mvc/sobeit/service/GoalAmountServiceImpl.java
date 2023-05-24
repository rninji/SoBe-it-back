package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.GoalAmountCntDTO;
import com.finalproject.mvc.sobeit.dto.GoalAmountDTO;
import com.finalproject.mvc.sobeit.dto.GoalAmountResponseDTO;
import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.GoalAmount;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.repository.ArticleRepo;
import com.finalproject.mvc.sobeit.repository.GoalAmountRepo;
import com.finalproject.mvc.sobeit.repository.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GoalAmountServiceImpl implements GoalAmountService{

    private final GoalAmountRepo goalAmountRep;
    private final UserRepo userRep;
    private final ArticleRepo articleRep;

    /**
     * 유저에 대한 성공한 도전과제 개수&도전과제 개수
     * @param userId
     * @return 성공한 도전과제 개수&도전과제 개수
     */
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

    public GoalAmount selectGoalAmountById(Long goalAmountSeq){
        return goalAmountRep.findById(goalAmountSeq).orElse(null);
    }

    public GoalAmountResponseDTO findGoalAmountResponse(String userId, Long goalAmountSeq){
        //보려는 도전과제 가져오기
        GoalAmount goalAmount = selectGoalAmountById(goalAmountSeq);

        Long consumption = 0L;// 기간동안 소비된 비용
        List<Article> articleList = articleRep.findArticlesByUser(userId);
        if (articleList == null){ // 게시글이 없는 경우
            throw new RuntimeException("소비한 비용이 없습니다.");
        }
        for (Article article : articleList){
            if (article.getConsumptionDate().isAfter(goalAmount.getStartDate()) && article.getConsumptionDate().isBefore(goalAmount.getEndDate())){
                consumption += article.getAmount();
            }
        }

        GoalAmountResponseDTO goalAmountResponseDTO = GoalAmountResponseDTO.builder()
                .goalAmount(goalAmount.getGoalAmount())
                .title(goalAmount.getTitle())
                .userId(userId)
                .startDate(goalAmount.getStartDate())
                .endDate(goalAmount.getEndDate())
                .isSuccess(goalAmount.getIsSuccess())
                .routine(goalAmount.getRoutine())
                .consumption(consumption)
                .build();

        return goalAmountResponseDTO;
    }

    @Override
    public List<GoalAmountResponseDTO> selectGoalAmount(String userId) {
        List<Long> goalAmountSeqList = goalAmountRep.findGoalAmountSeq(userId);
        if (goalAmountSeqList == null) throw new RuntimeException("도전과제가 없습니다.");

        List<GoalAmountResponseDTO> goalAmountList = new ArrayList<>();
        goalAmountSeqList.forEach(g -> goalAmountList.add(findGoalAmountResponse(userId, g)));
        return goalAmountList;
    }

    @Override
    public GoalAmount insertGoalAmount(@AuthenticationPrincipal Users user, GoalAmountDTO goalAmountDTO) throws RuntimeException{
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
