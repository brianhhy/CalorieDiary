package com.example.cal_dia_mem.junFood.dto;

import com.example.cal_dia_mem.foodCommend.dto.FoodCommendDTO;
import com.example.cal_dia_mem.foodCommend.entity.FoodCommendEntity;
import com.example.cal_dia_mem.junFood.entity.JunFoodEntity;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class JunFoodDTO {
    private long id;
    private String foodName;
    private String kcal;
    private String carbohydrate;
    private String protein;
    private String fat;
    private String sugars;
    private String salt;
    private String cholesterol;
    private String saturated_fatty;
    private String transfat;

    public static JunFoodDTO toJunFoodDTO(JunFoodEntity junFoodEntity){
        JunFoodDTO junFoodDTO = new JunFoodDTO();
        junFoodDTO.setId(junFoodEntity.getId());
        junFoodDTO.setFoodName(junFoodEntity.getFoodName());
        junFoodDTO.setKcal(junFoodEntity.getKcal());
        junFoodDTO.setCarbohydrate(junFoodEntity.getCarbohydrate());
        junFoodDTO.setProtein(junFoodEntity.getProtein());
        junFoodDTO.setFat(junFoodEntity.getFat());
        junFoodDTO.setSugars(junFoodEntity.getSugars());
        junFoodDTO.setSalt(junFoodEntity.getSalt());
        junFoodDTO.setCholesterol(junFoodEntity.getCholesterol());
        junFoodDTO.setSaturated_fatty(junFoodEntity.getSaturated_fatty());
        junFoodDTO.setTransfat(junFoodEntity.getTransfat());

        return junFoodDTO;
    }
}
