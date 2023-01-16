package io.github.parkjeongwoong.application.blog.dto;

import io.github.parkjeongwoong.entity.Visitor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

@Getter
@NoArgsConstructor
public class VisitorSaveRequestDto {
    private String url;
    private String lastPage;

    @Builder
    public VisitorSaveRequestDto(String url, String lastPage) {
        this.url = url;
        this.lastPage = lastPage;
    }

    public Visitor toEntity() {
        return Visitor.builder()
                .url(url)
                .lastPage(lastPage)
                .build();
    }
}
