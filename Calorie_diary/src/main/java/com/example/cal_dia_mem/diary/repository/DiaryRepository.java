package com.example.cal_dia_mem.diary.repository;

import com.example.cal_dia_mem.diary.entity.DiaryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity,Integer>{
    List<DiaryEntity> findByCreateDateAndMemberEmail(Date createDate, String memberEmail);
}
