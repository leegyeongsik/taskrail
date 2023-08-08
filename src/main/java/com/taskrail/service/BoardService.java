package com.taskrail.service;

import com.taskrail.dto.BoardInviteRequestDto;
import com.taskrail.dto.BoardRequestDto;
import com.taskrail.dto.BoardResponseDto;
import com.taskrail.dto.UserResponseDto;
import com.taskrail.entity.Board;
import com.taskrail.entity.BoardRole;
import com.taskrail.entity.User;
import com.taskrail.repository.BoardRepository;
import com.taskrail.repository.BoardRoleRepository;
import com.taskrail.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class BoardService {
    private final BoardRepository boardRepository;
    private final BoardRoleRepository boardRoleRepository;
    private final UserRepository userRepository;
    public BoardResponseDto createBoard(BoardRequestDto boardRequestDto , User user) { // 보드 생성
        Board board =  Board.builder().title(boardRequestDto.getTitle()).color(boardRequestDto.getColor()).description(boardRequestDto.getDescription()).user(user).build();
        System.out.println(board);
        boardRepository.save(board);
        return new BoardResponseDto(board);
    }

    public List<BoardResponseDto> getBoard(User user){ // 자신이 만든 보드 초대받은 보드 두개를 리스트에 넣어줌 -> +1해주는 이유는 조회하고 cnt를 산출할때 보드를 개설한 사람이 포함되지않아서 cnt+=1
        List<Board> boardList = boardRepository.findAllByUser_idOrderByCreatedAtDesc(user.getId());
        List<BoardResponseDto> boardResponseDtoList = new ArrayList<>();
        for (Board board : boardList) {
            Long BoardCnt=boardRepository.getBoardCount(board.getBoardId())+1;
            boardResponseDtoList.add(new BoardResponseDto(board,new UserResponseDto(board.getUser()) , BoardCnt));
        }
        List<Board>boardchildList = boardRepository.getBoardchildList(user.getId());
        for (Board board : boardchildList) {
            Long BoardCnt=boardRepository.getBoardCount(board.getBoardId())+1;
            boardResponseDtoList.add(new BoardResponseDto(board,new UserResponseDto(board.getUser()), BoardCnt));
        }
        return boardResponseDtoList;
    }

    public String inviteUser(Long board_id, BoardInviteRequestDto boardInviteRequestDto, User confirmUser){ // 토큰에있는 유저가 초대하려는 보드의 주인이 맞고 추가하려는 유저가 존재할때 유저초대
        Board board=confirmOwnedBoard(confirmUser,board_id);                                               // 초대할때 유저가 회원탈퇴하면 나머지 유저들 초대못하고 오류남
        List<Long>userIdList=boardInviteRequestDto.getUser_id();

        for (Long userId : userIdList) {
            User user = confirmUserId(userId);
            BoardRole boardRole = BoardRole.builder().board(board).user(user).build();
            boardRoleRepository.save(boardRole);
        }
        return "성공";
    }
    public List<UserResponseDto> getBoardUser(Long boardId) {
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        List<User> userList = boardRepository.getBoardUser(boardId);
        for (User user : userList) {
            userResponseDtoList.add(new UserResponseDto(user));
        }
        return userResponseDtoList;
    }
    public List<UserResponseDto> getSearch(Long board_id,String name) {
        List<UserResponseDto> userResponseDtoList = new ArrayList<>();
        List<User> userList = boardRepository.search(name);
        for (User user : userList) {
            // 보드아이디랑 user아이디랑 같은 값을 가지고있는 애들이 롤에 있냐 있으면 optional로 있으면 true 없으면 false
            Optional<BoardRole> isinUser=boardRoleRepository.findByUser_idAndBoard_boardId(user.getId(),board_id);
            if(!isinUser.isPresent()){
                userResponseDtoList.add(new UserResponseDto(user,true));
            } else {
                userResponseDtoList.add(new UserResponseDto(user,false));
            }
        }
        return userResponseDtoList;
    }

    public String getTargetBoard(Long boardId, User user) {
        return null;
    }
    public String deleteBoard(Long board_id,User confirmUser){
        Board board=confirmOwnedBoard(confirmUser,board_id);
        boardRepository.delete(board);
        return "삭제성공";
    }
    public String updateBoard(Long board_id,BoardRequestDto boardRequestDto,User confirmUser){
        Board board=confirmOwnedBoard(confirmUser,board_id);
        board.updateBoard(boardRequestDto);
        boardRepository.save(board);
        return "업데이트성공";
    }




    private Board confirmOwnedBoard(User user , Long board_id){
         Board board=boardRepository.findById(board_id).orElseThrow(() ->
                new IllegalArgumentException("board가 존재하지 않습니다 ")
        );

        if (user.getId().equals(board.getUser().getId())) {
        } else {
            throw new IllegalArgumentException("자신이 게시한 보드가 아닙니다");
        }
        return board;
    }
    private User confirmUserId(Long userId){
        User user=userRepository.findById(userId).orElseThrow(() -> // 여러개중에 하나라도 유저없으면 나머지유저들이 추가안됨
                new IllegalArgumentException("User가 존재하지 않습니다 ")
        );
        return user;
    }



}
