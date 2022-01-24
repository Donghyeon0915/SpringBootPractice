package com.example.springbootpractice.controller;

import com.example.springbootpractice.dto.ArticleFormDto;
import com.example.springbootpractice.entity.Article;
import com.example.springbootpractice.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Slf4j //로깅을 위한 어노테이션
@Controller
public class ArticleController {
    @Autowired  //스프링 부트가 미리 생성해놓은 객체를 가져다가 연결
    private ArticleRepository articleRepository;

    @GetMapping("/articles/new")    //게시글 생성 페이지로 이동
    public String newArticleForm(){
        return "articles/new"; //templates를 기준으로 articles/new.mustache
    }

    @PostMapping("/articles/create")  //게시글 생성 요청
    public String createArticle(ArticleFormDto requestDto){
        //println으로 로그를 찍으면 서버 성능 하락, 기록도 남지 않음 -> 실제 서버에서 사용하면 안됨
        log.info(requestDto.toString());  //System.out.println(requestDto); -> 로깅으로 대체

        //1. Dto를 Entity로 변환
        Article article = requestDto.toEntity();
        log.info(article.toString()); //System.out.println(article.toString());

        //2. Repository에게 Entity를 DB에 저장하게 함
        Article saved = articleRepository.save(article);
        log.info(saved.toString()); //System.out.println(saved.toString());

        return "redirect:/articles/" + saved.getId();  //제출하고 다시 원래 페이지로 돌아감(redirect를 사용하지 않으면 url이 바뀌지 않음(요청했던 url이 유지됨))
    }

    @GetMapping("/articles/{id}")  //{id}라는 변수를 지정
    public String show(@PathVariable Long id, Model model){
        log.info("id = " + id);
        
        // 1. Id로 데이터를 가져옴 - 데이터를 가져오는 주체는 Repository
        Article articleEntity = articleRepository.findById(id).orElse(null); //id로 데이터를 찾는데, 없으면 null을 반환

        // 2: 가져온 데이터를 모델에 등록
        model.addAttribute("article", articleEntity);

        // 3: 보여줄 페이지를 설정
        return "articles/show";
    }

    @GetMapping("/articles")
    public String index(Model model){
        //1. 모든 Article을 가져온다
        List<Article> articleEntityList = (List<Article>) articleRepository.findAll();
        //Iterable<Article> articleEntityList = (List<Article>) articleRepository.findAll();

        //2. 가져온 Article 묶음을 뷰로 전달
        model.addAttribute("articleList", articleEntityList);

        //3. 뷰 페이지를 설정
        return "articles/index";
    }

    @GetMapping("/articles/{id}/edit")
    public String edit(@PathVariable Long id, Model model){  //@PathVariable이랑 URL의 변수 이름이 같아야함
        // 수정할 데이터를 가져오기
        Article articleEnttiy = articleRepository.findById(id).orElse(null);

        //모델에 데이터 등록
        model.addAttribute("article", articleEnttiy);

        return "articles/edit"; //뷰 페이지 설정
    }

    @PostMapping("/articles/update")
    public String update(ArticleFormDto requestDto){
        log.info(requestDto.toString());
        // 1. Dto를 Entity로 변환
        Article articleEntity = requestDto.toEntity();
        log.info(articleEntity.toString());

        // 2. Entity를 DB로 저장
        // 2-1: DB에서 기존 데이터를 가져옴
        Article target = articleRepository.findById(articleEntity.getId()).orElse(null);

        // 2-2: 기존 데이터에 값을 갱신
        if(target != null){
            articleRepository.save(articleEntity); //엔티티가 DB로 갱신
        }

        // 3. 수정 결과 페이지로 리다이렉트
        return "redirect:/articles/" + articleEntity.getId();
    }
}
