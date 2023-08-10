package com.taskrail.controller;

import com.taskrail.dto.BoardResponseDto;
import com.taskrail.dto.ColumnResponseDto;
import com.taskrail.entity.Board;
import com.taskrail.repository.BoardRepository;
import com.taskrail.service.BoardService;
import com.taskrail.service.ColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@RequiredArgsConstructor
public class BoardViewController {

    private final ColumnService columnService;
    private final BoardRepository boardRepository;

    @GetMapping("view/board-page/{boardId}")
    public String showBoard(@PathVariable Long boardId, Model model) {
        Board board = columnService.findBoard(boardId);
        List<ColumnResponseDto> columns = board.getColumns().stream()
                .map(ColumnResponseDto::new)
                .collect(Collectors.toList());

        model.addAttribute("board", board);
        model.addAttribute("columns", columns);
        return "board";
    }

    @GetMapping("view/main/create")
    public String MainPageCreateBoard() {
        return "createboard";
    }

    @GetMapping("view/main/{tagId}/edit")
    public String MainPageEditBoard(@PathVariable Long tagId, Model model) {
        BoardResponseDto boardResponseDto = new BoardResponseDto(boardRepository.findById(tagId).get());
        model.addAttribute("Board" , boardResponseDto);
        return "boardedit";
    }
}
