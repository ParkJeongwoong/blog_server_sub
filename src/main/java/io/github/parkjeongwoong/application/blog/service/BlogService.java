package io.github.parkjeongwoong.application.blog.service;

import io.github.parkjeongwoong.application.blog.dto.*;
import io.github.parkjeongwoong.application.blog.repository.ArticleRepository;
import io.github.parkjeongwoong.application.blog.repository.VisitorRepository;
import io.github.parkjeongwoong.application.blog.usecase.BlogUsecase;
import io.github.parkjeongwoong.entity.Visitor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StreamUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class BlogService implements BlogUsecase {
    private final VisitorRepository visitorRepository;
    private final ArticleRepository articleRepository;

    public void visited(VisitorSaveRequestDto requestDto) {
        Visitor visitor = requestDto.toEntity();
        visitor.setData();
        System.out.println("Visitor just visited : " + visitor.getUrl());
        System.out.println("Visitor's IP address is : " + visitor.getIp());
        System.out.println("Current Time : " + new Date());

        if (isRecordable(visitor.getIp())) return ; // 구글 봇 (66.249.~) 와 내 ip (58.140.57.190) 제외
        visitorRepository.save(visitor);
    }

    @Transactional
    public long countVisitor() {
        return visitorRepository.count();
    }

    @Transactional(readOnly = true)
    public List<VisitorResponseDto> history() {
        return visitorRepository.findAllByOrderByIdDesc().stream()
                .map(VisitorResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PageVisitorResponseDto> countVisitor_page() {
        return visitorRepository.countVisitor_page().stream()
                .map(PageVisitorResponseDto::new)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public List<PageVisitorResponseDto> countVisitor_firstPage() {
        return visitorRepository.countVisitor_firstPage().stream()
                .map(PageVisitorResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<DailyVisitorResponseDto> countDailyVisitor() {
//        return null;
        return visitorRepository.countDailyVisitor().stream()
                .map(DailyVisitorResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<VisitorCountResponseDto> countVisitorRank() {
        return visitorRepository.countVisitor().stream()
                .map(VisitorCountResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<VisitorCountResponseDto> countVisitorRank_date(String startDate, String endDate) {
        return visitorRepository.countVisitor_date(startDate, endDate+"T23:59:59.99").stream()
                .map(VisitorCountResponseDto::new)
                .collect(Collectors.toList());
    }

    public List<ArticleResponseDto> getArticleList() {
        return articleRepository.findAllDesc().stream()
                .map(ArticleResponseDto::new)
                .collect(Collectors.toList());
    }

    public ArticleResponseDto getArticle(String category, Long categoryId) {
        return articleRepository.findByCategoryAndId(category, categoryId);
    }

    public byte[] getImage(String imageName) throws IOException {
        InputStream imageStream = new FileInputStream(System.getProperty("user.dir")
                + File.separator + "src"
                + File.separator + "main"
                + File.separator + "resources"
                + File.separator + "article_images"
                + File.separator + imageName);
        byte[] image = StreamUtils.copyToByteArray(imageStream);
        String image_string = Base64.getEncoder().encodeToString(image);
        imageStream.close();
        return Base64.getDecoder().decode(image_string);
    }

    private boolean isRecordable(String ip) {
        String[] notRecordableArray = {"58.140.57.190" // 공덕 ip
                                 , "222.110.245.239"}; // 키움증권 ip
        ArrayList<String> notRecordableList = new ArrayList<>(Arrays.asList(notRecordableArray));
        return notRecordableList.contains(ip) || Objects.equals(ip.substring(0,6), "66.249"); // 구글 봇
    }
}
