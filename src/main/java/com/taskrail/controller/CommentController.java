package com.taskrail.controller;

import com.taskrail.dto.*;
import com.taskrail.entity.Columns;
import com.taskrail.security.UserDetailsImpl;
import com.taskrail.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class CommentController {

    private final CommentService commentService;

    //1. 댓글 등록
    @PostMapping("/cards/{cardId}/comments")
    public ResponseEntity<ApiResponseDto> createComment(@PathVariable Long cardId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails ){
        commentService.createComment(cardId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("댓글 등록 완료", HttpStatus.CREATED.value()));
    }
    //2. 댓글 수정
    @PutMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponseDto> updateComment(@PathVariable Long commentId, @RequestBody CommentRequestDto requestDto, @AuthenticationPrincipal UserDetailsImpl userDetails ){
        commentService.updateComment(commentId, requestDto, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("댓글 수정 완료", HttpStatus.OK.value()));
    }

    //3. 댓글 삭제
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ApiResponseDto> deleteComment(@PathVariable Long commentId,  @AuthenticationPrincipal UserDetailsImpl userDetails ){
        commentService.deleteComment(commentId, userDetails.getUser());
        return ResponseEntity.ok().body(new ApiResponseDto("댓글 삭제 완료", HttpStatus.OK.value()));
    }

    //4. 예외 처리
    @ExceptionHandler({IllegalArgumentException.class})
    public ResponseEntity<RestApiResponseDto> handleMethodArgumentNotValidException(IllegalArgumentException ex) {
        RestApiResponseDto restApiException = new RestApiResponseDto(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }

    //5. 예외 처리
    @ExceptionHandler({NullPointerException.class})
    public ResponseEntity<RestApiResponseDto> handleMethodArgumentNotValidException(NullPointerException ex) {
        RestApiResponseDto restApiException = new RestApiResponseDto(HttpStatus.BAD_REQUEST.value(),ex.getMessage());
        return new ResponseEntity<>(
                // HTTP body
                restApiException,
                // HTTP status code
                HttpStatus.BAD_REQUEST
        );
    }
}
