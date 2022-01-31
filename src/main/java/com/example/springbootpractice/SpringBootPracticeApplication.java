package com.example.springbootpractice;

import com.example.springbootpractice.entity.Article;
import com.example.springbootpractice.entity.Comment;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class SpringBootPracticeApplication {

    public static void main(String[] args) {


        SpringApplication.run(SpringBootPracticeApplication.class, args);

/*        Comment comment = Comment.builder()
                .id(1L)
                .nickname("Park")
                .body("굳 윌 헌팅")
                .article(new Article(4L, "당신의 인생 영화는 ?", "댓글 ㄱ"))
                .build();

        log.info("아티클 가져오기 {}", comment.getArticle().toString());*/

    }

}
