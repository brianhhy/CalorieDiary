package com.example.cal_dia_mem.diary.dto;


import com.example.cal_dia_mem.diary.entity.DiaryEntity;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class DiaryDTO {

    private Integer id;
    private String memberEmail;
    private Date createDate;
    private String food_name;
    private String kcal;
    private String carbohydrate;
    private String protein;
    private String fat;
    private String sugars;
    private String salt;
    private String cholesterol;
    private String saturated_fatty;
    private String transfat;

    public static DiaryDTO toDiaryDTO(DiaryEntity diaryEntity){
        DiaryDTO diaryDTO = new DiaryDTO();
        diaryDTO.setId(diaryEntity.getId());
        diaryDTO.setMemberEmail(diaryEntity.getMemberEmail());
        diaryDTO.setCreateDate(diaryEntity.getCreateDate());
        diaryDTO.setFood_name(diaryEntity.getFood_name());
        diaryDTO.setKcal(diaryEntity.getKcal());
        diaryDTO.setCarbohydrate(diaryEntity.getCarbohydrate());
        diaryDTO.setProtein(diaryEntity.getProtein());
        diaryDTO.setFat(diaryEntity.getFat());
        diaryDTO.setSugars(diaryEntity.getSugars());
        diaryDTO.setSalt(diaryEntity.getSalt());
        diaryDTO.setCholesterol(diaryEntity.getCholesterol());
        diaryDTO.setSaturated_fatty(diaryEntity.getSaturated_fatty());
        diaryDTO.setTransfat(diaryEntity.getTransfat());

        return diaryDTO;
    }
}