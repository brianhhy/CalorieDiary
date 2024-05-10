package com.example.cal_dia_mem.diary.entity;

import com.example.cal_dia_mem.diary.dto.DiaryDTO;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;
import java.sql.Timestamp;

@Entity
@Setter
@Getter
@Table(name="food_diary")
@Transactional
public class DiaryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String memberEmail;
    @Column(columnDefinition = "DATE")
    @CreationTimestamp
    private Date createDate;
    @Column
    private String food_name;
    @Column
    private String kcal;
    @Column
    private String carbohydrate;
    @Column
    private String protein;
    @Column
    private String fat;
    @Column
    private String sugars;
    @Column
    private String salt;
    @Column
    private String cholesterol;
    @Column
    private String saturated_fatty;
    @Column
    private String transfat;

    public static DiaryEntity toDiaryEntity(DiaryDTO diaryDTO){
        DiaryEntity diaryEntity = new DiaryEntity();
        diaryEntity.setId(diaryDTO.getId());
        diaryEntity.setMemberEmail(diaryDTO.getMemberEmail());
        diaryEntity.setCreateDate(diaryDTO.getCreateDate());
        diaryEntity.setFood_name(diaryDTO.getFood_name());
        diaryEntity.setKcal(diaryDTO.getKcal());
        diaryEntity.setCarbohydrate(diaryDTO.getCarbohydrate());
        diaryEntity.setProtein(diaryDTO.getProtein());
        diaryEntity.setFat(diaryDTO.getFat());
        diaryEntity.setSugars(diaryDTO.getSugars());
        diaryEntity.setSalt(diaryDTO.getSalt());
        diaryEntity.setCholesterol(diaryDTO.getCholesterol());
        diaryEntity.setSaturated_fatty(diaryDTO.getSaturated_fatty());
        diaryEntity.setTransfat(diaryDTO.getTransfat());

        return diaryEntity;
    }


    public static DiaryDTO entityToDto(DiaryEntity entity){
        DiaryDTO dto = new DiaryDTO();
        dto.setId(entity.getId());
        dto.setMemberEmail(entity.getMemberEmail());
        dto.setCreateDate(entity.getCreateDate());
        dto.setFood_name(entity.getFood_name());
        dto.setKcal(entity.getKcal());
        dto.setCarbohydrate(entity.getCarbohydrate());
        dto.setProtein(entity.getProtein());
        dto.setFat(entity.getFat());
        dto.setSugars(entity.getSugars());
        dto.setSalt(entity.getSalt());
        dto.setCholesterol(entity.getCholesterol());
        dto.setSaturated_fatty(entity.getSaturated_fatty());
        dto.setTransfat(entity.transfat);
        return dto;
    }
}

