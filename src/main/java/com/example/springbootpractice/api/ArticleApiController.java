package com.example.springbootpractice.api;


import com.example.springbootpractice.dto.ArticleFormDto;
import com.example.springbootpractice.entity.Article;
import com.example.springbootpractice.repository.ArticleRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController //Rest API 용 컨트롤러(데이터(JSON)를 반환)
public class ArticleApiController {

    @Autowired //DI : Dependency Injecion 외부에서 가져온다
    private ArticleRepository articleRepository;
    // GET
    //url과 uri의 차이 ?
    @GetMapping("/api/articles")
    public List<Article> index(){
        return articleRepository.findAll();
    }

    @GetMapping("/api/articles/{id}")
    public Article index(@PathVariable Long id){
        return articleRepository.findById(id).orElse(null);
    }

    // POST
    @PostMapping("api/articles")
    /*
    * form에서 데이터를 던질 때는 파라미터에 추가만하면 받아와 지지만
    * RestAPI에서 JSON으로 데이터를 던질 때에는 @RequestBody(Request의 Body에서 dto를 받아오라는 뜻)를 붙여줘야한다.
    * */
    public Article create(@RequestBody ArticleFormDto requestDto){
        Article article = requestDto.toEntity();
        return articleRepository.save(article);
    }

    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id,
                                         @RequestBody ArticleFormDto requestDto){

        // 1: 수정용 엔티티 생성
        Article article = requestDto.toEntity();
        log.info("id : {}, article : {}", id, article.toString());

        // 2ㅣ 대상 엔티티 조회
        Article target = articleRepository.findById(id).orElse(null);

        // 3: 잘못된 요청 처리(대상이 없거나, id가 다른 경우

        if(target == null || id != article.getId()){
            //400, 잘못된 요청 응답
            log.info("잘못된 요청 id : {}, article : {}", id, article.toString());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
        target.patch(article);
        // 4: 업데이트 및 정상 응답(200)
        Article updated = articleRepository.save(target);

        return ResponseEntity.status(HttpStatus.OK).body(updated);
    }
    // DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        //대상 찾기
        Article target = articleRepository.findById(id).orElse(null);

        //잘못된 요청 처리
        if(target == null) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        //대상 삭제
        articleRepository.delete(target);

        //데이터 반환
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
