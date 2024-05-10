package com.example.cal_dia_mem.camera.dto;

import lombok.*;

//ocr 기능을 이용해 영양성분을 받아왔을때 필요한 코드

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OcrDTO {
    String foodName;
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

