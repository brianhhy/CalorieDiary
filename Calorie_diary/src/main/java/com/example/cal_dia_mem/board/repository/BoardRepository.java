package com.example.cal_dia_mem.board.repository;

import com.example.cal_dia_mem.board.entity.BoardEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface BoardRepository extends JpaRepository<BoardEntity,Integer> {
    List<BoardEntity> findTop5ByCreateDateAfterAndCreateDateBeforeOrderByViewDesc(LocalDateTime threeDaysAgo, LocalDateTime now);
}
