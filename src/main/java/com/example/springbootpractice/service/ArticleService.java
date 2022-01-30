package com.example.springbootpractice.service;

import com.example.springbootpractice.dto.ArticleFormDto;
import com.example.springbootpractice.entity.Article;
import com.example.springbootpractice.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service //서비스 선언 (서비스 객체를 스프링 부트에 생성) -> 해당 선언으로 인해 @Autowired로 받아와짐
public class ArticleService {

    @Autowired //DI : Dependency Injection
    private ArticleRepository articleRepository;

    public List<Article> index(){
        return articleRepository.findAll();
    }


    public Article show(Long id) {
        return articleRepository.findById(id).orElse(null);
    }

    public Article create(ArticleFormDto requestDto) {
        Article article = requestDto.toEntity();

        //게시글 등록 요청에 id 값이 같이 들어온 경우(id 값은 DB가 자동으로 생성하므로 사용자가 입력할 필요가 없음)
        if(article.getId() != null) return null; 
        
        return articleRepository.save(article);
    }

    public Article update(Long id, ArticleFormDto requestDto) {

        // 1. 수정용 엔티티 생성
        Article article = requestDto.toEntity();
        log.info("id : {}, article : {}", id, article.toString());
        // 2. 대상 엔티티 찾기
        Article target = articleRepository.findById(id).orElse(null);

        // 3. 잘못된 요청 처리(대상이 없거나, id가 다른 경우)
        if(target == null || id != article.getId()){
            log.info("잘못된 요청! id : {}, article : {}", id, article.toString());
            return null;
        }

        // 4. 업데이트
        target.patch(article);
        Article updated = articleRepository.save(target);

        return updated;
    }

    public Article delete(Long id) {
        Article target = articleRepository.findById(id).orElse(null);

        if(target == null) return null;

        articleRepository.delete(target);

        return target;
    }

    //트랜잭션은 서비스가 관리
    @Transactional //해당 메소드를 트랜잭션으로 묶는다 (실패하면 메소드가 실행되기 전으로 롤백)
    public List<Article> createArticles(List<ArticleFormDto> requestDtos) {
        // dto 묶음을 entity 묶음으로 변환
        List<Article> articleList = requestDtos.stream().map(dto -> dto.toEntity()).collect(Collectors.toList());

        // entity 묶음을 DB로 저장
        articleList.stream().forEach(article -> articleRepository.save(article));

        // 강제 예외 발생
        articleRepository.findById(-1L).orElseThrow(() -> new IllegalArgumentException("결재 실패 !"));

        // 결과값 반환
        return articleList;
    }
}
