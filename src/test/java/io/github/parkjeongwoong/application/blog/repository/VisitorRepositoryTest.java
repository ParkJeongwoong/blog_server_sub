package io.github.parkjeongwoong.application.blog.repository;

import io.github.parkjeongwoong.entity.Visitor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class VisitorRepositoryTest {

    @Autowired
    VisitorRepository visitorRepository;

    @AfterEach
    public void cleanup() { visitorRepository.deleteAll(); }

    @Test
    public void test_findAllByOrderByIdDesc() {
        // Given
        String url1 = "https://www.test.com";
        String ip1 = "127.0.0.1";
        boolean justVisited1 = true;
        Visitor visitor_set1 = Visitor.builder()
                .url(url1)
                .build();
        visitor_set1.setData();

        String url2 = "https://github.com/ParkJeongwoong/";
        String ip2 = "127.0.0.1";
        String lastPage2 = "https://www.test.com";
        boolean justVisited2 = false;
        Visitor visitor_set2 = Visitor.builder()
                .url(url2)
                .lastPage(lastPage2)
                .build();
        visitor_set2.setData();

        visitorRepository.save(visitor_set1);
        visitorRepository.save(visitor_set2);

        // When
        List<Visitor> visitorList = visitorRepository.findAllByOrderByIdDesc();

        // Then
        Visitor visitor_get1 = visitorList.get(0);
        Visitor visitor_get2 = visitorList.get(1);

        assertThat(visitor_get2.getUrl()).isEqualTo(url1);
        assertThat(visitor_get2.getIp()).isEqualTo(ip1);
        assertThat(visitor_get2.getLastPage()).isEqualTo(null);
        assertThat(visitor_get1.getUrl()).isEqualTo(url2);
        assertThat(visitor_get1.getIp()).isEqualTo(ip2);
        assertThat(visitor_get1.getLastPage()).isEqualTo(lastPage2);
    }
}
