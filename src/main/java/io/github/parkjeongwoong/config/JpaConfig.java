package io.github.parkjeongwoong.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@EnableJpaAuditing // 생성, 수정 시간 기록을 위한 JPA Auditing 활성화
public class JpaConfig {
}
