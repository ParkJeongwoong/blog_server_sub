package io.github.parkjeongwoong.application.blog.dto;

import io.github.parkjeongwoong.entity.Visitor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@Getter
@NoArgsConstructor
public class VisitorSaveRequestDto {
    private String url;
    private String lastPage;
    private String ip;
    private String referrer;

    @Builder
    public VisitorSaveRequestDto(String url, String lastPage, String referrer) {
        this.url = url;
        this.lastPage = lastPage;
        this.referrer = referrer;
    }

    public Visitor toEntity() {
        if (this.ip == null) setIp();
        return Visitor.builder()
                .url(url)
                .lastPage(lastPage)
                .ip(ip)
                .referrer(referrer)
                .build();
    }

    private void setIp() {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        String ip = request.getHeader("X-FORWARDED-FOR");
        log.info("X-FORWARDED-FOR : {}", ip);
        if (ip == null) {
            ip = request.getRemoteAddr();
            log.info("getRemoteAddr: {}", ip);
        }
        this.ip = ip;
    }
}
