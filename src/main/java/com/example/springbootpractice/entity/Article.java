package com.example.springbootpractice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@ToString
@NoArgsConstructor
@Entity
public class Article {
    @Id //대표값을 지정
    @GeneratedValue //auto increment
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
}