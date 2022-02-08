package com.example.springbootpractice.repository;

import com.example.springbootpractice.entity.Article;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

//<관리 대상 Entity, Entity의 Id 타입>
public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Override
    @Query("SELECT article FROM Article article ORDER BY article.id DESC")
    ArrayList<Article> findAll(); //반환 타입을 바꾸기위해서 오버라이드
}
