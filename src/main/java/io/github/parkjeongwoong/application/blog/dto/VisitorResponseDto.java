package io.github.parkjeongwoong.application.blog.dto;

import io.github.parkjeongwoong.entity.Visitor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class VisitorResponseDto {
    private final long id;
    private final String url;
    private final String ip;
    private final LocalDateTime visitedDate;
    private final String lastPage;
    private final String referrer;

    public VisitorResponseDto(Visitor entity) {
        this.id = entity.getId();
        this.url = entity.getUrl();
        this.ip = entity.getIp();
        this.lastPage = entity.getLastPage();
        this.visitedDate = entity.getCreatedDate();
        this.referrer = entity.getReferrer();
    }
}
