package io.github.parkjeongwoong.application.blog.dto;

import java.time.LocalDate;

public interface DailyVisitorResponseDtoInterface {
    LocalDate getDate();
    long getCount();
}
