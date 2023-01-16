package io.github.parkjeongwoong.application.blog.dto;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.LocalDate;

@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public class DailyVisitorResponseDto {
    private final LocalDate date;
    private final long count;

    public DailyVisitorResponseDto(DailyVisitorResponseDtoInterface entity) {
        this.date = entity.getDate();
        this.count = entity.getCount();
    }
}
