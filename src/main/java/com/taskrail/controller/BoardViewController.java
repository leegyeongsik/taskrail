package com.taskrail.controller;

import com.taskrail.dto.ColumnResponseDto;
import com.taskrail.entity.Board;
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

    @GetMapping("/board-page/{boardId}")
    public String showBoard(@PathVariable Long boardId, Model model) {
        Board board = columnService.findBoard(boardId);
        List<ColumnResponseDto> columns = board.getColumns().stream()
                .map(ColumnResponseDto::new)
                .collect(Collectors.toList());

        model.addAttribute("board", board);
        model.addAttribute("columns", columns);
        return "board";
    }
}
