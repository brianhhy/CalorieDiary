package com.example.cal_dia_mem.junFood.entity;

import com.example.cal_dia_mem.junFood.dto.JunFoodDTO;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Date;

@Entity
@Setter
@Getter
@Table(name="junFood")
public class JunFoodEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String foodName;
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

    public static JunFoodEntity JunFoodEntity(JunFoodDTO junFoodDTO){

        JunFoodEntity junFoodEntity =new JunFoodEntity();
        junFoodEntity.setId(junFoodEntity.getId());
        junFoodEntity.setFoodName(junFoodDTO.getFoodName());
        junFoodEntity.setKcal(junFoodDTO.getKcal());
        junFoodEntity.setCarbohydrate(junFoodDTO.getCarbohydrate());
        junFoodEntity.setProtein(junFoodDTO.getProtein());
        junFoodEntity.setFat(junFoodDTO.getFat());
        junFoodEntity.setSugars(junFoodDTO.getSugars());
        junFoodEntity.setSalt(junFoodDTO.getSalt());
        junFoodEntity.setCholesterol(junFoodDTO.getCholesterol());
        junFoodEntity.setSaturated_fatty(junFoodDTO.getSaturated_fatty());
        junFoodEntity.setTransfat(junFoodDTO.getTransfat());

        return junFoodEntity;
    }
}
