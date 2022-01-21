package com.example.springbootpractice.repository;

import com.example.springbootpractice.entity.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

import java.util.ArrayList;

//<관리 대상 Entity, Entity의 Id 타입>
public interface ArticleRepository extends CrudRepository<Article, Long> {
    @Override
    ArrayList<Article> findAll();
}
