package com.example.springbootpractice.dto;


import com.example.springbootpractice.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@ToString
@Builder
@AllArgsConstructor
public class ArticleFormDto {
    private Long id;
    private String title;
    private String content;

    public Article toEntity() {
        return Article.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();
    }
}
