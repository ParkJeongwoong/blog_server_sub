package io.github.parkjeongwoong.application.blog.service;

import io.github.parkjeongwoong.application.blog.dto.VisitorSaveRequestDto;
import io.github.parkjeongwoong.application.blog.repository.VisitorRepository;
import io.github.parkjeongwoong.entity.Visitor;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class BlogServiceTest {

    @Autowired
    private VisitorRepository visitorRepository;

    @Autowired
    private BlogService blogService;

    @Autowired
    MockHttpServletRequest request;

    @BeforeEach
    public void setup() {
        request = new MockHttpServletRequest();
        request.addHeader("X-FORWARDED-FOR", "127.0.0.1");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @AfterEach
    public void cleanup() { visitorRepository.deleteAll(); }

    @Test
    public void test_visited() throws Exception {
        // Given
        String localAddress = "127.0.0.1";
        String testUrl1 = "https://www.test.com";
        String testUrl2 = "https://github.com/ParkJeongwoong/";

        VisitorSaveRequestDto requestDto1 = VisitorSaveRequestDto.builder()
                .url(testUrl1)
                .build();
        VisitorSaveRequestDto requestDto2 = VisitorSaveRequestDto.builder()
                .url(testUrl2)
                .lastPage(testUrl1)
                .build();

        // When
        blogService.visited(requestDto1);
        blogService.visited(requestDto2);

        // Then
        List<Visitor> visitorList = visitorRepository.findAll();
        Visitor visitor1 = visitorList.get(0);
        Visitor visitor2 = visitorList.get(1);
        assertThat(visitor1.getIp()).isEqualTo(localAddress);
        assertThat(visitor1.getUrl()).isEqualTo(testUrl1);
        assertThat(visitor1.getLastPage()).isEqualTo(null);
        assertThat(visitor1.isJustVisited()).isEqualTo(true);
        assertThat(visitor2.getIp()).isEqualTo(localAddress);
        assertThat(visitor2.getUrl()).isEqualTo(testUrl2);
        assertThat(visitor2.getLastPage()).isEqualTo(testUrl1);
        assertThat(visitor2.isJustVisited()).isEqualTo(false);
    }
}
