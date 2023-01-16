package io.github.parkjeongwoong.application.blog.dto;

import lombok.Getter;

@Getter
public class CommonResponseDto {
    private final String requestName;
    private final String jogResult;
    private final String message;

    public CommonResponseDto(String requestName, String jogResult, String message) {
        this.requestName = requestName;
        this.jogResult = jogResult;
        this.message = message;
    }
}
