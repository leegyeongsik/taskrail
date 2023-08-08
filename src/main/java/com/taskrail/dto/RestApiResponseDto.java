package com.taskrail.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RestApiResponseDto {
    // 클라이언트 쪽에 응답과 메시지 반환

    private int statusCode;
    private String resultMessage;
    //private Object result;
}
