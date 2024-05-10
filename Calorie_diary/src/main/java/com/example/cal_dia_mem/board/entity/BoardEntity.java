package com.example.cal_dia_mem.board.entity;

import com.example.cal_dia_mem.board.dto.BoardDTO;
import com.example.cal_dia_mem.board.service.BoardService;
import com.example.cal_dia_mem.diary.dto.DiaryDTO;
import com.example.cal_dia_mem.diary.entity.DiaryEntity;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Time;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Setter
@Getter
@Table(name="Board_table")
@Data
public class  BoardEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private String memberEmail;
    private String memberNickname;
    private Integer view;
    @Column(name="create_date")
    private LocalDateTime createDate;


    public LocalDateTime getCreateDate() {
        return createDate;
    }

    public void setCreateDate(LocalDateTime createDate) {
        this.createDate = createDate.truncatedTo(ChronoUnit.MINUTES);
    }
    public static BoardEntity toBoardEntity(BoardDTO boardDTO){
        BoardEntity boardEntity = new BoardEntity();
        boardEntity.setTitle(boardDTO.getTitle());
        boardEntity.setContent(boardDTO.getContent());
        boardEntity.setMemberEmail(boardDTO.getMemberEmail());
        boardEntity.setMemberNickname(boardDTO.getMemberNickname());
        boardEntity.setView(boardDTO.getView());
        return boardEntity;
    }

    public static BoardDTO entityToDto(BoardEntity entity){
        BoardDTO dto = new BoardDTO();
        dto.setId(entity.getId());
        dto.setTitle(entity.getTitle());
        dto.setContent(entity.getContent());
        dto.setMemberEmail(entity.getMemberEmail());
        dto.setMemberNickname(entity.getMemberNickname());
        dto.setView(entity.getView());
        dto.setCreateDate(entity.getCreateDate());

        return dto;
    }
}


