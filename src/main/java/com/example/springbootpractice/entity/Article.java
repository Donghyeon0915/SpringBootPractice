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
        /*
         * 사용자가 입력을 하지않았으면 null이 들어오므로
         * 값이 있다는 것은 수정을 했다는 뜻
         */
        if(article.title != null) this.title = article.getTitle();
        if(article.content != null) this.content = article.getContent();
    }
}
