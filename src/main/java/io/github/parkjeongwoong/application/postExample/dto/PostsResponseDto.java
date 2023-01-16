package io.github.parkjeongwoong.application.postExample.dto;

import io.github.parkjeongwoong.entity.example.Posts;
import lombok.Getter;

@Getter
public class PostsResponseDto {

    private final long id;
    private final String title;
    private final String content;
    private final String author;

    public PostsResponseDto(Posts entity) {
        this.id = entity.getId();
        this.title = entity.getTitle();
        this.content = entity.getContent();
        this.author = entity.getAuthor();
    }

}
