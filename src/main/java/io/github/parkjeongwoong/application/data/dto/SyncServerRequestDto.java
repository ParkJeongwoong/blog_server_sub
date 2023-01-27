package io.github.parkjeongwoong.application.data.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Getter
public class SyncServerRequestDto {
    private final String password;

    @Builder
    public SyncServerRequestDto(String password) {
        this.password = password;
    }
}
