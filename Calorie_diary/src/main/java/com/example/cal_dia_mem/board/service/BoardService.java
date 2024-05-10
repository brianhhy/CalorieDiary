package com.example.cal_dia_mem.board.service;

import com.example.cal_dia_mem.board.dto.BoardDTO;
import com.example.cal_dia_mem.board.entity.BoardEntity;
import com.example.cal_dia_mem.board.repository.BoardRepository;
import com.example.cal_dia_mem.diary.dto.DiaryDTO;
import com.example.cal_dia_mem.diary.entity.DiaryEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;
    public void Write(BoardDTO boardDTO){
        BoardEntity boardEntity = BoardEntity.toBoardEntity(boardDTO);
        boardEntity.setView(0);
        LocalDateTime now =LocalDateTime.now().truncatedTo(ChronoUnit.MINUTES);
        boardEntity.setCreateDate(now);
        boardRepository.save(boardEntity);
    }
    public void Modify(BoardEntity boardEntity){
        boardRepository.save(boardEntity);
    }
    public Page<BoardEntity> boardList(Pageable pageable) {
        return boardRepository.findAll(pageable);
    }

    public BoardEntity boardView(Integer id)
    {
        Optional<BoardEntity> boardEntity = this.boardRepository.findById(id);
        if(boardEntity.isPresent()) {
            BoardEntity boardEntity1 = boardEntity.get();
            boardEntity1.setView(boardEntity1.getView()+1);
            this.boardRepository.save(boardEntity1);
        }
        return boardRepository.findById(id).get();
    }
    //특정 게시물 불러오기

    public void boardDelete(Integer id){
        boardRepository.deleteById(id);
    }


    public List<BoardDTO> popularBoard() {

        LocalDateTime now = LocalDateTime.now();
        LocalDateTime threeDaysAgo = now.minusDays(3);
        Pageable pageable = PageRequest.of(0, 5);
        List<BoardEntity> popularBoardEntity=boardRepository.findTop5ByCreateDateAfterAndCreateDateBeforeOrderByViewDesc(threeDaysAgo,now);


        List<BoardDTO> poplarBoardDto = popularBoardEntity.stream()
                .map(BoardEntity::entityToDto)
                .collect(Collectors.toList());

        System.out.print(poplarBoardDto);
        return poplarBoardDto;
    }
}
