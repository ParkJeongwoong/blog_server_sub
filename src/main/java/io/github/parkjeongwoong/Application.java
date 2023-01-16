package io.github.parkjeongwoong;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

// HelloControllerTest 용도로 EnableJpaAuditing 주석 처리 (config/JpaConfig 로 이동)
//@EnableJpaAuditing // 생성, 수정 시간 기록을 위한 JPA Auditing 활성화
@SpringBootApplication
public class Application {

    @PostConstruct
    void setKST() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"));
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
