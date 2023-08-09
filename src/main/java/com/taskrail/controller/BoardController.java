package com.taskrail.controller;


import com.taskrail.dto.BoardInviteRequestDto;
import com.taskrail.dto.BoardRequestDto;
import com.taskrail.dto.BoardResponseDto;
import com.taskrail.dto.UserResponseDto;
import com.taskrail.security.UserDetailsImpl;
import com.taskrail.service.BoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.security.KeyStore;
import java.util.List;

@RestController
@RequestMapping("/api/boards")
@RequiredArgsConstructor
public class BoardController {
    private final BoardService boardService;
    @PostMapping("") // 보드생성
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto boardRequestDto , @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return boardService.createBoard(boardRequestDto , userDetails.getUser());
    }

    @GetMapping("") // 로그인한 유저가 속해있는 보드
    public List<BoardResponseDto> getBoard(@AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.getBoard(userDetails.getUser());
    }

    @PostMapping("/{board_id}") // 유저 초대
    public String inviteUser(@PathVariable Long board_id ,
                             @RequestBody BoardInviteRequestDto boardInviteRequestDto,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.inviteUser(board_id,boardInviteRequestDto,userDetails.getUser());
    }
    @GetMapping("/{board_id}/users") // 해당 보드의 유저
    public List<UserResponseDto> getBoardUser(@PathVariable Long board_id){
        return boardService.getBoardUser(board_id);
    }
    @DeleteMapping("/{board_id}") // 해당 보드 삭제
    public String deleteBoard(@PathVariable Long board_id ,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.deleteBoard(board_id,userDetails.getUser());
    }
    @PutMapping("/{board_id}") // 해당 보드 업데이트
    public String updateBoard(@PathVariable Long board_id ,
                             @RequestBody BoardRequestDto boardRequestDto,
                             @AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.updateBoard(board_id,boardRequestDto,userDetails.getUser());
    }

    @GetMapping("/{board_id}/search") // 검색하고 검색한거에 맞는 사람 쭉 나오는데 해당 보드에 이미 초대된 사람이라면 true로 넣어줌 아니면 false
    public List<UserResponseDto> getSearch(@PathVariable Long board_id,
                                            @RequestParam("name")String name){
        return boardService.getSearch(board_id,name);
    }

    @GetMapping("/{board_id}") // 상세 보드
    public BoardResponseDto getTargetBoard(@PathVariable Long board_id,@AuthenticationPrincipal UserDetailsImpl userDetails){
        return boardService.getTargetBoard(board_id, userDetails.getUser());
    }
}
