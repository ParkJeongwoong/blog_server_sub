package io.github.parkjeongwoong.application.blog.usecase;

import io.github.parkjeongwoong.application.blog.dto.ArticleUpdateRequestDto;
import io.github.parkjeongwoong.application.blog.dto.CommonResponseDto;
import org.springframework.web.multipart.MultipartHttpServletRequest;

public interface FileUsecase {
    CommonResponseDto saveArticle(MultipartHttpServletRequest multiRequest);
    CommonResponseDto updateArticle_string(Long articleId, ArticleUpdateRequestDto requestDto);
    CommonResponseDto updateArticle_markdown(Long articleId, MultipartHttpServletRequest multiRequest);
    CommonResponseDto deleteArticle(Long articleId);
}
