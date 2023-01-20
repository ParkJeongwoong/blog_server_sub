package io.github.parkjeongwoong.application.data.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.parkjeongwoong.application.blog.dto.MailSendDto;
import io.github.parkjeongwoong.application.blog.usecase.MailingUsecase;
import io.github.parkjeongwoong.application.data.dto.SyncServerRequestDto;
import io.github.parkjeongwoong.application.data.usecase.DataUsecase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;

@Slf4j
@RequiredArgsConstructor
@Service
public class ServerSynchronizingService {

    private final MailingUsecase mailingUsecase;
    private final DataUsecase dataUsecase;
    private WebClient webClient;

    @Value("${main.server}")
    String mainServer;
    @Value("${backup.db.main.ip}")
    String mainServerIp;
    @Value("${backup.db.main.pw}")
    String mainServerPw;

    @PostConstruct
    public void initWebClient() {
        webClient = WebClient.builder().defaultHeader(HttpHeaders.CONTENT_TYPE, "application/json").build();
    }

    @Scheduled(cron = "0 5 * * * *")
    private void mainServerHealthCheck() {
        log.info("Main Server Ping Check");
        if (ping("main")) {
            log.info("Main Server is Good!");
        } else {
            log.warn("WARNING!! Your Main Server doesn't respond. Check Your Server Status!");
            log.warn("If you haven't received any email, check Sub Server Error Mail Function.");
        }
    }

    public void getVisitorTableFromMainServer() {
        log.info("Get Visitor dump data from main server.");
        if (isMainServerNotExist()) return;

        SyncServerRequestDto requestDto = SyncServerRequestDto.builder().password(mainServerPw).build();
        String BACKUP_URL = mainServer + "/data-api/sync";
        ObjectMapper mapper = new ObjectMapper();

        try {
            String jsonString = mapper.writeValueAsString(requestDto);

            webClient.post()
                    .uri(BACKUP_URL)
                    .body(BodyInserters.fromValue(jsonString))
                    .retrieve()
                    .bodyToMono(String.class)
                    .block();

            log.info("Backup From : {}", mainServer);
        } catch (JsonProcessingException e) {
            log.error("Visitor Table Sync Error", e);
        }
    }

    private boolean ping(String address) {

        try {

            if (address.equals("main")) address = mainServerIp;
            InetAddress inetAddress = InetAddress.getByName(address);

            if (inetAddress.isReachable(2000)) {
                log.info("Reachable IP : {}", address);
                return true;
            } else {
                log.info("Unreachable IP : {}", address);
                sendMainServerPingErrorMail();
            }

        } catch (UnknownHostException e) {
            log.error("IP 주소 문제", e);
        } catch (IOException e) {
            log.error("isReachable 문제", e);
        }

        return false;

    }

    private void sendMainServerPingErrorMail() {
        log.warn("Main Server Ping Error 발생!!");
        MailSendDto mailSendDto = makeMainServerPingErrorMail();
        mailingUsecase.sendMail(mailSendDto);
    }

    private MailSendDto makeMainServerPingErrorMail() {
        return MailSendDto.builder()
                .address("dvlprjw@gmail.com")
                .title("[Woong's Blog] Main Server - Ping Error")
                .content("Main Server Ping Error occurred. Check your blog's main server.")
                .build();
    }

    private boolean isMainServerNotExist() { return mainServer == null || mainServer.length() == 0; }

}
