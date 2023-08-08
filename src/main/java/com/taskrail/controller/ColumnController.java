package com.taskrail.controller;

import com.taskrail.dto.ColumnRequestDto;
import com.taskrail.dto.ColumnRequestDto;
import com.taskrail.dto.ColumnResponseDto;
import com.taskrail.entity.Columns;
import com.taskrail.security.UserDetailsImpl;
import com.taskrail.security.UserDetailsImpl;
import com.taskrail.service.ColumnService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ColumnController {
    private final ColumnService columnService;

    // 컬럼 생성
    @PostMapping("/columns")
    public ResponseEntity<Columns> createColumn(@AuthenticationPrincipal UserDetailsImpl userDetails, @RequestBody ColumnRequestDto columnRequestDto) {
        Columns newColumn = columnService.createColumn(userDetails.getUser(), columnRequestDto);
        return ResponseEntity.ok(newColumn);
    }

    // 컬럼 이름 수정
    @PutMapping("/columns/{id}")
    public ResponseEntity<String> updateColumn(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id, @RequestBody ColumnRequestDto columnRequestDto) {
        columnService.updateColumnTitle(userDetails.getUser(), id, columnRequestDto);
        return ResponseEntity.ok("컬럼 이름이 변경 됐습니다.");
    }

    // 컬럼 삭제
    @DeleteMapping("/columns/{id}")
    public ResponseEntity<String> deleteColumn(@AuthenticationPrincipal UserDetailsImpl userDetails, @PathVariable Long id) {
        columnService.deleteColumn(userDetails.getUser(), id);
        return ResponseEntity.ok("컬럼이 삭제됐습니다.");
    }

    // 컬럼 오른쪽으로 이동
    @PutMapping("/columns/{id}/right")
    public ResponseEntity<String> moveColumnRight(@PathVariable Long id) {
        columnService.moveColumn(id, 1);
        return ResponseEntity.ok("컬럼을 오른쪽으로 이동했습니다.");
    }

    // 컬럼 왼쪽으로 이동
    @PutMapping("/columns/{id}/left")
    public ResponseEntity<String> moveColumnLeft(@PathVariable Long id) {
        columnService.moveColumn(id, -1);
        return ResponseEntity.ok("컬럼을 왼쪽으로 이동했습니다.");
    }


}
