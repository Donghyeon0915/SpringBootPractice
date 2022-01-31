package com.example.springbootpractice.service;

import com.example.springbootpractice.dto.ArticleFormDto;
import com.example.springbootpractice.entity.Article;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest // 해당 클래스는 스프링 부트와 연동되어 테스팅된다
class ArticleServiceTest {

    @Autowired
    ArticleService articleService;

    @Test
    void index() {
        // 예상
        Article a = new Article(1L, "가가가가", "1111");
        Article b = new Article(2L, "나나나나", "2222");
        Article c = new Article(3L, "다다다다", "3333");

        List<Article> expected = new ArrayList<>(Arrays.asList(a,b,c));

        // 실제
        List<Article> articles = articleService.index();

        // 비교
        assertEquals(expected.toString(), articles.toString());
    }

    @Test //존재하는 id를 입력해서 성공하는 경우
    void show_success_input_existId() {
        // 예상
        Long id = 1L;
        Article expected = Article.builder()
                .id(id)
                .title("가가가가")
                .content("1111")
                .build();

        // 실제
        Article article = articleService.show(id);
        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Test // 존재하지 않는 id 입력
    void show_fail_input_not_existId(){
        // 예상
        Long id = -1L;
        Article expected = null; // show() 메소드에서 없는 경우엔 null을 리턴하므로 예상 값은 null

        // 실제
        Article article = articleService.show(id);
        // 비교
        assertEquals(expected, article); //null은 toString()을 호출할 수 없으므로 지워줌
    }

    @Transactional //DB의 데이터가 생성,수정,삭제가 될 수 있다면 Transaction으로 묶어야함
    @Test //title과 content만 있는 Dto를 요청으로 받았을 때
    void create_success_title_content_dto() {
        // 예상
        String title = "라라라라";
        String content = "4444";
        ArticleFormDto requestDto = ArticleFormDto.builder()
                .id(null)
                .title(title)
                .content(content)
                .build();

        Article expected = new Article(4L, title, content);

        // 실제
        Article article = articleService.create(requestDto);
        // 비교
        assertEquals(expected.toString(), article.toString()); //null은 toString()을 호출할 수 없으므로 지워줌
    }

    @Test // id가 포함된 Dto를 요청으로 받았을 때
    void create_fail_included_id_dto(){
        // 예상
        String title = "라라라라";
        String content = "4444";
        ArticleFormDto requestDto = ArticleFormDto.builder()
                .id(4L)
                .title(title)
                .content(content)
                .build();

        Article expected = null;

        // 실제
        Article article = articleService.create(requestDto);
        // 비교
        assertEquals(expected, article); //null은 toString()을 호출할 수 없으므로 지워줌
    }

    @Transactional
    @Test //존재하는 id와 title,content가 있는 dto 입력
    void update_success_1() {
        // 준비
        Long id = 1L;
        String title = "updated_title";
        String content = "updated_content";
        ArticleFormDto requestDto = ArticleFormDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();

        // 예상
        Article expected = requestDto.toEntity();

        // 실제
        Article article = articleService.update(id, requestDto);
        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Transactional
    @Test //존재하는 id와 title만 있는 dto 입력
    void update_success_2(){
        // 준비
        Long id = 1L;
        String title = "가가가가";
        String content = null;

        ArticleFormDto requestDto =  ArticleFormDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();

        // 예상 (업데이트 후의 예상 값을 지정)
        Article expected = new Article(id, title, "1111");

        // 실제
        Article article = articleService.update(id, requestDto);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Transactional
    @Test //존재하지 않는 id의 dto 입력
    void update_fail_1(){
        // 예상
        String title = "updated_title";
        String content = "updated_content";
        ArticleFormDto requestDto =  ArticleFormDto.builder()
                .id(4L)
                .title(title)
                .content(content)
                .build();

        Article expected = null;

        // 실제
        Article article = articleService.update(4L, requestDto);

        // 비교
        assertEquals(expected, article);

    }

    @Transactional
    @Test //id만 있는 dto 입력
    void update_fail_2(){
        // 예상
        Long id = 1L;
        String title = null;
        String content = null;
        ArticleFormDto requestDto =  ArticleFormDto.builder()
                .id(id)
                .title(title)
                .content(content)
                .build();

        Article expected = articleService.show(id);

        // 실제
        Article article = articleService.update(id, requestDto);

        // 비교
        assertEquals(expected.toString(), article.toString());
    }

    @Transactional
    @Test //존재하는 id 입력
    void delete_success(){
        // 예상
        Long id = 1L;
        Article expected = articleService.show(id);

        // 실제
        Article article = articleService.delete(id);

        // 비교

        assertEquals(expected.toString(), article.toString());
    }

    @Transactional
    @Test //존재하지 않는 id 입력
    void delete_fail() {
        // 예상
        Article expected = null;

        // 실제
        Article article = articleService.show(4L);

        // 비교
        assertEquals(expected, article);
    }
}