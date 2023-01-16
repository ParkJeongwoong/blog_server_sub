//package io.github.parkjeongwoong.adapter.controller;
//
//import com.fasterxml.jackson.databind.ObjectMapper;
//import io.github.parkjeongwoong.application.blog.dto.VisitorSaveRequestDto;
//import io.github.parkjeongwoong.application.blog.repository.ArticleRepository;
//import io.github.parkjeongwoong.application.blog.repository.VisitorRepository;
//import io.github.parkjeongwoong.application.blog.repository.ImageRepository;
//import io.github.parkjeongwoong.entity.Visitor;
//import org.junit.jupiter.api.AfterEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.MediaType;
//import org.springframework.test.context.junit.jupiter.SpringExtension;
//import org.springframework.test.web.servlet.MockMvc;
//import org.springframework.test.web.servlet.MvcResult;
//
//import java.time.LocalDateTime;
//import java.util.List;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.hamcrest.Matchers.*;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
//
//@ExtendWith(SpringExtension.class)
//@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//@AutoConfigureMockMvc
//public class BlogApiControllerTest {
//    @LocalServerPort
//    private int port;
//
//    @Autowired
//    private VisitorRepository visitorRepository;
//
//    @Autowired
//    private ArticleRepository articleRepository;
//
//    @Autowired
//    private ImageRepository imageRepository;
//
//    public void visitors_setup() {
//        Visitor visitor1 = Visitor.builder()
//                .url("https://www.test.com")
//                .ip("127.0.0.1")
//                .justVisited(true).build();
//
//        Visitor visitor2 = Visitor.builder()
//                .url("https://github.com/ParkJeongwoong/")
//                .ip("127.0.0.1")
//                .lastPage("https://www.test.com")
//                .justVisited(false).build();
//
//        visitorRepository.save(visitor1);
//        visitorRepository.save(visitor2);
//    }
//
//    @AfterEach
//    public void tearDown() {
//        visitorRepository.deleteAll();
//        articleRepository.deleteAll();
//        imageRepository.deleteAll();
//    }
//
//    @Autowired
//    private MockMvc mvc;
//
//    @Test
//    public void test_visited() throws Exception {
//        // Given
//        String url = "http://localhost:" + port + "/blog-api/visited";
//        String localAddress = "127.0.0.1";
//        String testUrl1 = "https://www.test.com";
//        String testUrl2 = "https://github.com/ParkJeongwoong/";
//
//        VisitorSaveRequestDto requestDto1 = VisitorSaveRequestDto.builder()
//                .url(testUrl1)
//                .build();
//        VisitorSaveRequestDto requestDto2 = VisitorSaveRequestDto.builder()
//                .url(testUrl2)
//                .lastPage(testUrl1)
//                .build();
//
//        // When
//        mvc.perform(post(url)
//                .contentType(MediaType.APPLICATION_JSON)
//                .content(new ObjectMapper().writeValueAsString(requestDto1)))
//                .andExpect(status().isOk());
//        mvc.perform(post(url)
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(new ObjectMapper().writeValueAsString(requestDto2)))
//                .andExpect(status().isOk());
//
//        // Then
//        List<Visitor> visitorList = visitorRepository.findAll();
//        Visitor visitor1 = visitorList.get(0);
//        Visitor visitor2 = visitorList.get(1);
//        assertThat(visitor1.getIp()).isEqualTo(localAddress);
//        assertThat(visitor1.getUrl()).isEqualTo(testUrl1);
//        assertThat(visitor1.getLastPage()).isEqualTo(null);
//        assertThat(visitor1.isJustVisited()).isEqualTo(true);
//        assertThat(visitor2.getIp()).isEqualTo(localAddress);
//        assertThat(visitor2.getUrl()).isEqualTo(testUrl2);
//        assertThat(visitor2.getLastPage()).isEqualTo(testUrl1);
//        assertThat(visitor2.isJustVisited()).isEqualTo(false);
//    }
//
//    @Test
//    public void test_history() throws Exception {
//        // Given
//        LocalDateTime now = LocalDateTime.now();
//        System.out.println(now);
//        String url = "http://localhost:" + port + "/blog-api/history";
//        String localAddress = "127.0.0.1";
//        String testUrl1 = "https://www.test.com";
//        String testUrl2 = "https://github.com/ParkJeongwoong/";
//
//        visitors_setup();
//
//        // When & Then
//        MvcResult mvcResult = mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0].url", is(testUrl2)))
//                .andExpect(jsonPath("$[0].ip", is(localAddress)))
//                .andExpect(jsonPath("$[0].visitedDate",
//                        matchesPattern(now.getYear() + "-"
//                                + String.format("%02d", now.getMonthValue()) + "-"
//                                + String.format("%02d", now.getDayOfMonth()) + "T"
//                                + String.format("%02d", now.getHour()) + ":"
//                                + String.format("%02d", now.getMinute()) + ".*"))) // 주의 : 밀리초가 넘어가면서 초, 분, 시, 일이 넘어가 now와 차이날 수 있음
//                .andExpect(jsonPath("$[0].lastPage", is(testUrl1)))
//                .andExpect(jsonPath("$[1].url", is(testUrl1)))
//                .andExpect(jsonPath("$[1].ip", is(localAddress)))
//                .andExpect(jsonPath("$[1].visitedDate",
//                        matchesPattern(now.getYear() + "-"
//                                + String.format("%02d", now.getMonthValue()) + "-"
//                                + String.format("%02d", now.getDayOfMonth()) + "T"
//                                + String.format("%02d", now.getHour()) + ":"
//                                + String.format("%02d", now.getMinute()) + ".*"))) // 주의 : 밀리초가 넘어가면서 초, 분, 시, 일이 넘어가 now와 차이날 수 있음
//                .andExpect(jsonPath("$[1].lastPage").doesNotExist())
//                .andReturn();
//        String content = mvcResult.getResponse().getContentAsString();
//        System.out.println(content);
//    }
//
//    @Test
//    public void test_count_visitor() throws Exception {
//        // Given
//        String url = "http://localhost:" + port + "/blog-api/count-visitor";
//        visitors_setup();
//
//        // When
//        MvcResult mvcResult = mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andReturn();
//
//        // Then
//        String content = mvcResult.getResponse().getContentAsString();
//        assertThat(content).isEqualTo("2");
//    }
//
//    @Test
//    public void test_count_visitor_page() throws Exception {
//        // Given
//        String url = "http://localhost:" + port + "/blog-api/page-visitor";
//        String testUrl1 = "https://www.test.com";
//        String testUrl2 = "https://github.com/ParkJeongwoong/";
//        visitors_setup();
//
//        // When
//        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0].url", is(testUrl2)))
//                .andExpect(jsonPath("$[0].count", is(1)))
//                .andExpect(jsonPath("$[1].url", is(testUrl1)))
//                .andExpect(jsonPath("$[1].count", is(1)));
//    }
//
//    @Test
//    public void test_count_visitor_firstPage() throws Exception {
//        // Given
//        String url = "http://localhost:" + port + "/blog-api/first-visits";
//        String testUrl1 = "https://www.test.com";
//        String testUrl2 = "https://github.com/ParkJeongwoong/";
//        visitors_setup();
//
//        // When
//        mvc.perform(get(url).contentType(MediaType.APPLICATION_JSON))
//                .andExpect(status().isOk())
//                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$[0].url", is(testUrl1)))
//                .andExpect(jsonPath("$[0].count", is(1)))
//                .andExpect(jsonPath("$[1].url").doesNotExist());
//    }
//}
