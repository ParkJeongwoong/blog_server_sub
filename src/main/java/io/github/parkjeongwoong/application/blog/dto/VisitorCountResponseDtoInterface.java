package io.github.parkjeongwoong.application.blog.dto;

import java.time.LocalDate;

public interface VisitorCountResponseDtoInterface {
    LocalDate getDate();
    String getIp();
    long getCount();
}
