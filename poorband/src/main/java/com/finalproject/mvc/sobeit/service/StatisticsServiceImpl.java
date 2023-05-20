package com.finalproject.mvc.sobeit.service;

import com.finalproject.mvc.sobeit.dto.ExpenditureResponseDTO;
import com.finalproject.mvc.sobeit.entity.Article;
import com.finalproject.mvc.sobeit.entity.Users;
import com.finalproject.mvc.sobeit.repository.ArticleRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class StatisticsServiceImpl implements StatisticsService{
    private final ArticleRepo articleRepo;
    /**
     * 월별 지출 내역 가져오기
     *
     * @param user
     * @param year
     * @param month
     * @return
     */
    @Override
    public Map<Integer, List<?>> getExpenditure(Users user, int year, int month) {
        Long userSeq = user.getUserSeq();
        Map<Integer, List<?>> expMap = new HashMap<>();
        // 1일~31일 일별 지출 가져오기
        for(int i=1; i<32;i++) {
            expMap.put(i,getExpenditureDay(userSeq, year, month, i));
        }
        return expMap;
    }

    /**
     * 일별 지출 내역 가져오기
     * @param userSeq
     * @param year
     * @param month
     * @param day
     * @return
     */
    List<ExpenditureResponseDTO> getExpenditureDay(Long userSeq, int year, int month, int day){
        LocalDate date = LocalDate.of(year, month, day);
        // 유저가 그 날 쓴 지출 글 가져오기
        List<Article> articleList = articleRepo.findExpenditureArticlesByConsumptionDate(userSeq, date);

        // ResponseDTOList로 변환
        List<ExpenditureResponseDTO> expenditureResponseDTOList = new ArrayList<>();
        for (Article article : articleList) {
            // 가계부 메모 가져오기
            String context = article.getFinancialText();
            // 가계부 메모가 없다면 글 내용 가져오기
            if (context == null || context.length()==0){
                context = article.getArticleText();
            }
            // ResponseDTO 생성
            ExpenditureResponseDTO expenditureResponseDTO = ExpenditureResponseDTO.builder()
                    .expenditureCategory(article.getExpenditureCategory())
                    .context(context)
                    .amount(article.getAmount())
                    .articleSeq(article.getArticleSeq())
                    .consumptionDate(article.getConsumptionDate())
                    .build();
            // 리스트에 추가
            expenditureResponseDTOList.add(expenditureResponseDTO);
        }

        return expenditureResponseDTOList;
    }

}
