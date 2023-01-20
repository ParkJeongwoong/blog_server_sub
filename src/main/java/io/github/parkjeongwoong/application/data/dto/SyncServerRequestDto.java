package io.github.parkjeongwoong.application.data.dto;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
public class SyncServerRequestDto {
    private final String password;

    @Builder
    public SyncServerRequestDto(String password) {
        this.password = password;
    }
}
