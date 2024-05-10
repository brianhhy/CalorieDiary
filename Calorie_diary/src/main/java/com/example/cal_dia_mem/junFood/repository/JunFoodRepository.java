package com.example.cal_dia_mem.junFood.repository;

import com.example.cal_dia_mem.junFood.entity.JunFoodEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JunFoodRepository extends JpaRepository<JunFoodEntity,Integer> {
    Optional<JunFoodEntity> findById(Integer id);
}
