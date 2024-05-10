package com.example.cal_dia_mem.diary.service;
import com.example.cal_dia_mem.diary.dto.DiaryDTO;
import com.example.cal_dia_mem.diary.entity.DiaryEntity;
import com.example.cal_dia_mem.diary.repository.DiaryRepository;
import com.example.cal_dia_mem.foodCommend.dto.FoodCommendDTO;
import com.example.cal_dia_mem.profile.service.ProfileService;
import jakarta.persistence.criteria.CriteriaBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DiaryService {
    private final DiaryRepository diaryRepository;
    private final ProfileService profileService;

    // 칼로리 다이어리에 섭취 음식 및 영양성분 저장
    public void save(DiaryDTO diaryDTO) {
        DiaryEntity diaryEntity = DiaryEntity.toDiaryEntity(diaryDTO);
        diaryRepository.save(diaryEntity);
    }
    public void diaryDelete(Integer id){
        diaryRepository.deleteById(id);
    }

    // 칼로리 다이어리 DB로부터 섭취 음식 목록 받아오기
    public List<DiaryDTO> callDiary(Date createDate, String myEmail){
        List<DiaryEntity> diaryEntityList =diaryRepository.findByCreateDateAndMemberEmail(createDate,myEmail);

        System.out.println(diaryEntityList);


        // entity 리스트를 dto 리스트로 변환하는 스트림
        List<DiaryDTO> diaryDtoList = diaryEntityList.stream()
                .map(DiaryEntity::entityToDto)
                .collect(Collectors.toList());

        System.out.println(diaryDtoList);
        return diaryDtoList;
    }

    public List<DiaryDTO> idDiary(Integer id){
        Optional<DiaryEntity> diaryEntityList =diaryRepository.findById(id);
        List<DiaryDTO> diaryDtoList = diaryEntityList.stream()
                .map(DiaryEntity::entityToDto)
                .collect(Collectors.toList());
        return diaryDtoList;
    }

    //초과한 영양분 받아오기
    public List<String> overNutrient(Date createDate, String myEmail){
        List<String> overNutrient = new ArrayList<>();
        String gen= profileService.returnGen(myEmail);
        String currentWeight = profileService.returnCurrentWeight(myEmail);
        List<DiaryEntity> diaryEntityList =diaryRepository.findByCreateDateAndMemberEmail(createDate,myEmail);

        // entity 리스트를 dto 리스트로 변환하는 스트림
        List<DiaryDTO> diaryDtoList = diaryEntityList.stream()
                .map(DiaryEntity::entityToDto)
                .collect(Collectors.toList());

        if(gen==null)return null;
        if(gen.equals("m")) {

            if (400 < totalCarbohydrate(diaryDtoList)){
                overNutrient.add("탄수화물");
            }

            if (Double.parseDouble(currentWeight) * 1.2 < totalProtein(diaryDtoList)) {
                overNutrient.add("단백질");
            }

            if (65 < totalFat(diaryDtoList)){
                overNutrient.add("지방");
            }
        }
        else if(gen.equals("w")){

            if (350 < totalCarbohydrate(diaryDtoList)){
                overNutrient.add("탄수화물");
            }

            if (Double.parseDouble(currentWeight) * 1.2 < totalProtein(diaryDtoList)) {
                overNutrient.add("단백질");
            }

            if (65 < totalFat(diaryDtoList)){
                overNutrient.add("지방");
            }
        }
        return overNutrient;
    }

    // 부족한 영양분 받아오기
    public List<String> scarceNutrient(Date createDate, String myEmail){
        List<String> scarceNutrient = new ArrayList<>();
        String gen = profileService.returnGen(myEmail);
        String currentWeight = profileService.returnCurrentWeight(myEmail);
        List<DiaryEntity> diaryEntityList =diaryRepository.findByCreateDateAndMemberEmail(createDate,myEmail);

        // entity 리스트를 dto 리스트로 변환하는 스트림
        List<DiaryDTO> diaryDtoList = diaryEntityList.stream()
                .map(DiaryEntity::entityToDto)
                .collect(Collectors.toList());

        if(gen==null)return null;
        if(gen.equals("m")) {
            double mScarceCarbo = 110-totalCarbohydrate(diaryDtoList);

            if (mScarceCarbo>=1){
                scarceNutrient.add("탄수화물이 최소권장 섭취량보다"+String.format("%.1f",mScarceCarbo)+"g만큼 부족합니다.");
            }

            double mScarcePro = Double.parseDouble(currentWeight) * 0.85-totalProtein(diaryDtoList);
            if (mScarcePro>=1) {
                scarceNutrient.add("단백질이 체중별 권장 섭취량보다"+String.format("%.1f",mScarcePro)+"g만큼 부족합니다.");
            }

            double mScarceFat=45-totalFat(diaryDtoList);
            if (mScarceFat>=1){
                scarceNutrient.add("지방이 최소권장 섭취량보다"+String.format("%.1f",mScarceFat)+"g만큼 부족합니다.");
            }
        }
        else if(gen.equals("w")){

            double wScarceCarbo = 100-totalCarbohydrate(diaryDtoList);
            if (wScarceCarbo>=1){
                scarceNutrient.add("탄수화물이 최소권장 섭취량보다"+String.format("%.1f",wScarceCarbo)+"g만큼 부족합니다.");
            }

            double wScarcePro = Double.parseDouble(currentWeight) * 0.85-totalProtein(diaryDtoList);
            if (wScarcePro>=1) {
                scarceNutrient.add("단백질이 체중별 권장 섭취량보다"+String.format("%.1f",wScarcePro)+"g만큼 부족합니다.");
            }

            double wScarceFat=40-totalFat(diaryDtoList);
            if (wScarceFat>=1){
                scarceNutrient.add("지방이 최소권장 섭취량보다"+String.format("%.1f",wScarceFat)+"g만큼 부족합니다.");
            }
        }
        return scarceNutrient;
    }




    //오늘 섭취한 탄수화물의 합 구하기
    public double totalCarbohydrate(List<DiaryDTO> diaryDtoList){
        double carbohydrateValue;
        double carbohydrateSum = 0.0;

        for(DiaryDTO dto : diaryDtoList){
            try {
                carbohydrateValue = Double.parseDouble(dto.getCarbohydrate());
                carbohydrateSum+=carbohydrateValue;
            } catch (NumberFormatException e){
                System.err.println("숫자로 변환할 수 없습니다1.");
            }
        }
        System.out.println("오늘섭취 탄수 :"+carbohydrateSum);
        return carbohydrateSum;
    }

    // 오늘 섭취한 단백질 합 구하기
    public double totalProtein(List<DiaryDTO> diaryDtoList){
        double proteinValue;
        double proteinSum = 0.0;

        for(DiaryDTO dto : diaryDtoList){
            try {
                proteinValue = Double.parseDouble(dto.getProtein());
                proteinSum+=proteinValue;
            } catch (NumberFormatException e){
                System.err.println("숫자로 변환할 수 없습니다2.");
            }
        }
        System.out.println("오늘섭취 단백 :"+proteinSum);
        return proteinSum;
    }

    //오늘 섭취한 지방 합 구하기
    public double totalFat(List<DiaryDTO> diaryDtoList){
        double fatValue;
        double fatSum = 0.0;

        for(DiaryDTO dto : diaryDtoList){
            try {
                fatValue = Double.parseDouble(dto.getFat());
                fatSum+=fatValue;
            } catch (NumberFormatException e){
                System.err.println("숫자로 변환할 수 없습니다3.");
            }
        }
        System.out.println("오늘섭취 지방 :"+fatSum);
        return fatSum;
    }
    public double totalKcal(List<DiaryDTO> diaryDtoList){
        double kcalValue;
        double kcalSum = 0.0;

        for(DiaryDTO dto : diaryDtoList){
            try {
                kcalValue = Double.parseDouble(dto.getKcal());
                kcalSum+=kcalValue;
            } catch (NumberFormatException e){
                System.err.println("숫자로 변환할 수 없습니다1.");
            }
        }
        System.out.println("오늘섭취 탄수 :"+kcalSum);
        return kcalSum;
    }
    public double totalSugars(List<DiaryDTO> diaryDtoList){
        double sugarsValue;
        double sugarsSum = 0.0;

        for(DiaryDTO dto : diaryDtoList){
            try {
                sugarsValue = Double.parseDouble(dto.getSugars());
                sugarsSum+=sugarsValue;
            } catch (NumberFormatException e){
                System.err.println("숫자로 변환할 수 없습니다1.");
            }
        }
        System.out.println("오늘섭취 탄수 :"+sugarsSum);
        return sugarsSum;
    }

    public double totalSalt(List<DiaryDTO> diaryDtoList){
        double saltValue;
        double slatSum = 0.0;

        for(DiaryDTO dto : diaryDtoList){
            try {
                saltValue = Double.parseDouble(dto.getSalt());
                slatSum+=saltValue;
            } catch (NumberFormatException e){
                System.err.println("숫자로 변환할 수 없습니다1.");
            }
        }
        System.out.println("오늘섭취 탄수 :"+slatSum);
        return slatSum;
    }


}