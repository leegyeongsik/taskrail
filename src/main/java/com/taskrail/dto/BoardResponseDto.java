package com.taskrail.dto;


import com.taskrail.entity.Board;
import com.taskrail.entity.User;
import lombok.Getter;

@Getter
public class BoardResponseDto {
    Long boardId;
    String title;
    String color;
    String description;
    Long boardCnt;
    UserResponseDto user;

    public BoardResponseDto(Board board){
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.color = board.getColor();
        this.description = board.getDescription();
    }
    public BoardResponseDto(Board board,UserResponseDto userResponseDto){
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.color = board.getColor();
        this.description = board.getDescription();
        this.user = userResponseDto;

    }
    public BoardResponseDto(Board board , UserResponseDto userResponseDto,Long boardCnt){
        this.boardId = board.getBoardId();
        this.title = board.getTitle();
        this.color = board.getColor();
        this.description = board.getDescription();
        this.boardCnt = boardCnt;
        this.user = userResponseDto;

    }
}
