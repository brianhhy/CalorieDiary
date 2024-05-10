package com.example.cal_dia_mem.foodCommend.service;

import com.example.cal_dia_mem.diary.dto.DiaryDTO;
import com.example.cal_dia_mem.diary.entity.DiaryEntity;
import com.example.cal_dia_mem.diary.service.DiaryService;
import com.example.cal_dia_mem.foodCommend.dto.FoodCommendDTO;
import com.example.cal_dia_mem.foodCommend.entity.FoodCommendEntity;
import com.example.cal_dia_mem.foodCommend.repository.FoodCommendRepository;
import com.example.cal_dia_mem.member.entity.SiteUserEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FoodCommendService {

    private final FoodCommendRepository foodCommendRepository;
    @Autowired
    private final DiaryService diaryService;

    //날짜를 입력 받아 랜덤하게 추출
    public int randomNum(int i) {
        int retNum = 0;
        java.util.Date today = new java.util.Date();
        Date sqlDate = new Date(today.getTime());
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(sqlDate);


        // 연, 월, 일 분리
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH) + 1; // 월은 0부터 시작하므로 1을 더해줍니다.
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        if(i==1){
            retNum=(((year+month+day)*month*month/day)*3) %5+1;
        }else if(i==2){
            retNum=(((year-month-day)*day/month)*3) %5+6;
        }else if(i==3){
            retNum=(((year+month-day)*month/day)*3) %5+11;
        }
        return retNum;
    }

    //
    public List<FoodCommendDTO> commendFood() {
        Long id1= (long) randomNum(1);
        Long id2= (long) randomNum(2);
        Long id3= (long) randomNum(3);

        List<FoodCommendDTO> foodCommendDTOList = new ArrayList<>();
        FoodCommendDTO foodCommendDTO;

        Optional<FoodCommendEntity> foodCommendEntity1= foodCommendRepository.findById(id1);
        if(foodCommendEntity1.isPresent()){
            FoodCommendEntity foodCommendEntity =foodCommendEntity1.get();
            foodCommendDTO=FoodCommendDTO.toFoodCommendDTO(foodCommendEntity);
            foodCommendDTOList.add(foodCommendDTO);
        }
        Optional<FoodCommendEntity> foodCommendEntity2= foodCommendRepository.findById(id2);
        if(foodCommendEntity2.isPresent()){
            FoodCommendEntity foodCommendEntity =foodCommendEntity2.get();
            foodCommendDTO=FoodCommendDTO.toFoodCommendDTO(foodCommendEntity);
            foodCommendDTOList.add(foodCommendDTO);
        }
        Optional<FoodCommendEntity> foodCommendEntity3= foodCommendRepository.findById(id3);
        if(foodCommendEntity3.isPresent()){
            FoodCommendEntity foodCommendEntity =foodCommendEntity3.get();
            foodCommendDTO=FoodCommendDTO.toFoodCommendDTO(foodCommendEntity);
            foodCommendDTOList.add(foodCommendDTO);
        }
        return foodCommendDTOList;

    }

    public String foodCommendInfo (List<FoodCommendDTO> foodCommendDTOList){
        double carbohydrate=totalCarbohydrate(foodCommendDTOList);
        double protein = totalProtein(foodCommendDTOList);
        double fat = totalFat(foodCommendDTOList);
        double kcal =totalKcal(foodCommendDTOList);
        String info="위 식단을 모두 섭취 시 총 "+kcal+"칼로리를 섭취 하실 수 있습니다. 이 안에 포함된 영양 성분은 총"+
                +carbohydrate+"g의 탄수화물, "+protein+"g의 단백질, "+fat+"g의 지방 입니다.";
        return info;

    }

    //탄수화물 합
    public double totalCarbohydrate(List<FoodCommendDTO> foodCommendDTOList){
        double carbohydrateValue;
        double carbohydrateSum = 0.0;

        for(FoodCommendDTO dto : foodCommendDTOList){
            try {
                carbohydrateValue = Double.parseDouble(dto.getCarbohydrate());
                carbohydrateSum+=carbohydrateValue;
            } catch (NumberFormatException e){
                System.err.println("숫자로 변환할 수 없습니다1.");
            }
        }

        return carbohydrateSum;
    }

    // 단백질 합 구하기
    public double totalProtein(List<FoodCommendDTO> foodCommendDTOList){
        double proteinValue;
        double proteinSum = 0.0;

        for(FoodCommendDTO dto : foodCommendDTOList){
            try {
                proteinValue = Double.parseDouble(dto.getProtein());
                proteinSum+=proteinValue;
            } catch (NumberFormatException e){
                System.err.println("숫자로 변환할 수 없습니다2.");
            }
        }
        return proteinSum;
    }

    //지방 합 구하기
    public double totalFat(List<FoodCommendDTO> foodCommendDTOList){
        double fatValue;
        double fatSum = 0.0;

        for(FoodCommendDTO dto : foodCommendDTOList){
            try {
                fatValue = Double.parseDouble(dto.getFat());
                fatSum+=fatValue;
            } catch (NumberFormatException e){
                System.err.println("숫자로 변환할 수 없습니다3.");
            }
        }
        return fatSum;
    }

    //칼로리 합
    public double totalKcal(List<FoodCommendDTO> foodCommendDTOList){
        double kcalValue;
        double kcalSum = 0.0;

        for(FoodCommendDTO dto : foodCommendDTOList){
            try {
                kcalValue = Double.parseDouble(dto.getKcal());
                kcalSum+=kcalValue;
            } catch (NumberFormatException e){
                System.err.println("숫자로 변환할 수 없습니다3.");
            }
        }

        return kcalSum;
    }

}
