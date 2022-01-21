package com.example.springbootpractice.dto;


import com.example.springbootpractice.entity.Article;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.ToString;

@ToString
@AllArgsConstructor
public class ArticleFormDto {
    private String title;
    private String content;

    public Article toEntity() {
        return Article.builder()
                .id(null)
                .title(title)
                .content(content)
                .build();
    }
}
