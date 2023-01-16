package io.github.parkjeongwoong.application.blog.dto;

import io.github.parkjeongwoong.entity.Visitor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VisitorCountResponseDto {
    private final String ip;
    private final long count;

    public VisitorCountResponseDto(VisitorCountResponseDtoInterface entity) {
        this.ip = entity.getIp();
        this.count = entity.getCount();

    }
}
