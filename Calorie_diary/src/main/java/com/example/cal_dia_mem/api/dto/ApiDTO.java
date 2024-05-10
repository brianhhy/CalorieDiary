package com.example.cal_dia_mem.api.dto;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class ApiDTO {

    String num;
    String food_name;
    String maker_name;
    String serving_size;
    String kcal;
    String carbohydrate;
    String protein;
    String fat;
    String sugars;
    String salt;
    String cholesterol;
    String saturated_fatty;
    String transfat;
}

