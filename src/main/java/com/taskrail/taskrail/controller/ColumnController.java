package com.taskrail.taskrail.controller;

import com.taskrail.taskrail.dto.ColumnRequestDto;
import com.taskrail.taskrail.dto.ColumnResponseDto;
import com.taskrail.taskrail.security.UserDetailsImpl;
import com.taskrail.taskrail.service.ColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ColumnController {
    private final ColumnService columnService;
    @PostMapping("/columns")
    public ColumnResponseDto createColumn(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ColumnRequestDto columnRequestDto) {
        return columnService.createColumn(userDetails.getUser(), columnRequestDto);
    }
    @PutMapping("/columns/{id}")
    public ColumnResponseDto updateColumn(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody ColumnRequestDto columnRequestDto) {
        return columnService.updateColumnTitle(userDetails.getUser(), id, columnRequestDto);
    }

    @DeleteMapping("/columns/{id}")
    public void deleteColumn(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        columnService.deleteColumn(userDetails.getUser(), id);
    }
}
