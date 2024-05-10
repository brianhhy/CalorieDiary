package com.example.cal_dia_mem.junFood.service;

import com.example.cal_dia_mem.junFood.dto.JunFoodDTO;
import com.example.cal_dia_mem.junFood.entity.JunFoodEntity;
import com.example.cal_dia_mem.junFood.repository.JunFoodRepository;
import com.example.cal_dia_mem.profile.entity.ProfileEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class JunFoodService {
    @Autowired
    private final JunFoodRepository junFoodRepository;

    public JunFoodDTO flushJunFood(Integer id){
        Optional<JunFoodEntity> optionalJunFoodEntity = junFoodRepository.findById(id);
        if(optionalJunFoodEntity.isPresent()){
            JunFoodEntity junFoodEntity =optionalJunFoodEntity.get();
            System.out.println("ddddddddddddddddd"+junFoodEntity.getCholesterol());
            JunFoodDTO junFoodDTO = JunFoodDTO.toJunFoodDTO(junFoodEntity);

            return junFoodDTO;
        }
        return null;
    }
}
