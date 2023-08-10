package com.taskrail.controller;

import com.taskrail.dto.ColumnRequestDto;
import com.taskrail.dto.ColumnResponseDto;
import com.taskrail.entity.Columns;
import com.taskrail.security.UserDetailsImpl;
import com.taskrail.service.ColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class ColumnController {
    private final ColumnService columnService;

    // 컬럼 생성
    @PostMapping("/columns")
    public ColumnResponseDto createColumn(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ColumnRequestDto columnRequestDto) {
        return columnService.createColumn(userDetails.getUser(), columnRequestDto);
    }

    // 컬럼 이름 수정
    @PutMapping("/columns/{id}")
    public ResponseEntity<ColumnResponseDto> updateColumn(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody ColumnRequestDto columnRequestDto) {
        ColumnResponseDto updatedColumn = columnService.updateColumnTitle(userDetails.getUser(), id, columnRequestDto);
        return ResponseEntity.ok(updatedColumn);
    }

    // 컬럼 삭제
    @DeleteMapping("/columns/{id}")
    public ResponseEntity<String> deleteColumn(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        columnService.deleteColumn(userDetails.getUser(), id);
        return ResponseEntity.ok("컬럼이 삭제됐습니다.");
    }

    // 컬럼 오른쪽으로 이동
    @PutMapping("/columns/{id}/right")
    public ResponseEntity<String> moveColumnRight(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        columnService.moveColumn(userDetails.getUser(), id, 1);
        return ResponseEntity.ok("컬럼을 오른쪽으로 이동했습니다.");
    }

    // 컬럼 왼쪽으로 이동
    @PutMapping("/columns/{id}/left")
    public ResponseEntity<String> moveColumnLeft(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        columnService.moveColumn(userDetails.getUser(), id, -1);
        return ResponseEntity.ok("컬럼을 왼쪽으로 이동했습니다.");
    }


}
