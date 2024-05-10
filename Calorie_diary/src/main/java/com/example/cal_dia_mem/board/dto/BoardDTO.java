package com.example.cal_dia_mem.board.dto;

import lombok.*;
import org.springframework.cglib.core.Local;

import java.sql.Time;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardDTO {
    private Integer id;
    private String title;
    private String content;
    private String memberEmail;
    private String memberNickname;
    private Integer view;
    private LocalDateTime createDate;
}
