package io.github.parkjeongwoong.entity;

import io.github.parkjeongwoong.application.blog.dto.VisitorSaveRequestDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;

public class VisitorTest {

    @Autowired
    MockHttpServletRequest request;

    @BeforeEach
    public void setup() {
        request = new MockHttpServletRequest();
        request.addHeader("X-FORWARDED-FOR", "127.0.0.1");
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
    }

    @Test
    public void test_setData() {
        // Given
        String testUrl1 = "https://www.test.com";
        String testUrl2 = "https://github.com/ParkJeongwoong/";
        Visitor visitor1 = Visitor.builder()
                .url(testUrl1)
                .build();
        Visitor visitor2 = Visitor.builder()
                .url(testUrl2)
                .lastPage(testUrl1)
                .build();

        // When
        visitor1.setData();
        visitor2.setData();

        // Then
        assertThat(visitor1.getIp()).isEqualTo("127.0.0.1");
        assertThat(visitor1.getUrl()).isEqualTo(testUrl1);
        assertThat(visitor1.getLastPage()).isEqualTo(null);
        assertThat(visitor1.isJustVisited()).isEqualTo(true);
        assertThat(visitor2.getIp()).isEqualTo("127.0.0.1");
        assertThat(visitor2.getUrl()).isEqualTo(testUrl2);
        assertThat(visitor2.getLastPage()).isEqualTo(testUrl1);
        assertThat(visitor2.isJustVisited()).isEqualTo(false);
    }
}
