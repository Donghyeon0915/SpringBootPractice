package com.example.springbootpractice.entity;

import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@NoArgsConstructor
@Entity //해당 클래스로 DB 테이블을 만든다
public class Article {
    @Id //대표값을 지정
    @GeneratedValue(strategy = GenerationType.IDENTITY) //DB가 id를 자동 생성 (auto increment)
    private Long id;

    @Column
    private String title;

    @Column
    private String content;

    @Builder
    public Article(Long id, String title, String content) {
        this.id = id;
        this.title = title;
        this.content = content;
    }

    public void patch(Article article){
        if(article.title != null) this.title = article.getTitle();
        if(article.content != null) this.content = article.getContent();
    }
}
