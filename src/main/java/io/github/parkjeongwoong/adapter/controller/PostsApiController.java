package io.github.parkjeongwoong.adapter.controller;

import io.github.parkjeongwoong.application.postExample.service.PostsService;
import io.github.parkjeongwoong.application.postExample.dto.PostsResponseDto;
import io.github.parkjeongwoong.application.postExample.dto.PostsSaveRequestDto;
import io.github.parkjeongwoong.application.postExample.dto.PostsUpdateRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
public class PostsApiController {

    private final PostsService postsService;

    @PostMapping("/api/v1/posts")
    public long save(@RequestBody PostsSaveRequestDto requestDto) {
        return postsService.save(requestDto);
    }

    @PutMapping("/api/v1/posts/{id}")
    public long update(@PathVariable long id, @RequestBody PostsUpdateRequestDto requestDto) {
        return postsService.update(id, requestDto);
    }

    @GetMapping("/api/v1/posts/{id}")
    public PostsResponseDto findById(@PathVariable long id) {
        return postsService.findById(id);
    }

    @DeleteMapping("/api/v1/posts/{id}")
    public long delete(@PathVariable long id) {
        postsService.delete(id);
        return id;
    }

}
