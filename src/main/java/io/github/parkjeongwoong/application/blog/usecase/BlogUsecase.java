package io.github.parkjeongwoong.application.blog.usecase;

import io.github.parkjeongwoong.application.blog.dto.*;

import java.io.IOException;
import java.util.List;

public interface BlogUsecase {
    void visited(VisitorSaveRequestDto requestDto);
    long countVisitor();
    List<VisitorResponseDto> history();
    List<PageVisitorResponseDto> countVisitor_page();
    List<PageVisitorResponseDto> countVisitor_firstPage();
    List<DailyVisitorResponseDto> countDailyVisitor();
    List<VisitorCountResponseDto> countVisitorRank();
    List<VisitorCountResponseDto> countVisitorRank_date(String star, String end);
    List<ArticleResponseDto> getArticleList();
    ArticleResponseDto getArticle(String category, Long categoryId);
    byte[] getImage(String imageName) throws IOException;
}
