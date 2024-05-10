package com.example.cal_dia_mem.foodCommend.repository;

import com.example.cal_dia_mem.foodCommend.entity.FoodCommendEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface FoodCommendRepository extends JpaRepository<FoodCommendEntity,Long> {
    Optional<FoodCommendEntity> findById(Long id);
}
