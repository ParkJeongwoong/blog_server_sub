package io.github.parkjeongwoong.application.blog.usecase;

import io.github.parkjeongwoong.application.blog.dto.MailSendDto;

public interface MailingUsecase {
    void sendMail(MailSendDto mailSendDto);
}
