package io.github.parkjeongwoong.application.blog.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MailSendDto {
    private String address;
    private String title;
    private String content;

    @Builder
    public MailSendDto(String address, String title, String content) {
        this.address = address;
        this.title = title;
        this.content = content;
    }
}
