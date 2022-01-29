package com.example.springbootpractice.api;


import com.example.springbootpractice.dto.ArticleFormDto;
import com.example.springbootpractice.entity.Article;
import com.example.springbootpractice.repository.ArticleRepository;
import com.example.springbootpractice.service.ArticleService;
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
    private ArticleService articleService;

    // GET
    @GetMapping("/api/articles")  //url과 uri의 차이 ?
    public List<Article> index(){ return articleService.index(); }

    @GetMapping("/api/articles/{id}")
    public Article index(@PathVariable Long id){
        return articleService.show(id);
    }

    //Post
    /*  form에서 데이터를 던질 때는 파라미터에 추가만하면 받아와 지지만
     * RestAPI에서 JSON으로 데이터를 던질 때에는 @RequestBody(Request의 Body에서 dto를 받아오라는 뜻)를 붙여줘야한다.
     */
    @PostMapping("/api/articles")
    public ResponseEntity<Article> create(@RequestBody ArticleFormDto requestDto){
        Article created = articleService.create(requestDto);

        return (created != null) ?
                ResponseEntity.status(HttpStatus.OK).body(created) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    // PATCH
    @PatchMapping("/api/articles/{id}")
    public ResponseEntity<Article> update(@PathVariable Long id,
                                         @RequestBody ArticleFormDto requestDto){

        Article updated = articleService.update(id, requestDto);

        return (updated != null) ?
                ResponseEntity.status(HttpStatus.OK).body(updated) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }


    // DELETE
    @DeleteMapping("/api/articles/{id}")
    public ResponseEntity<Article> delete(@PathVariable Long id){
        Article deleted = articleService.delete(id);

        //데이터 반환
        return (deleted != null) ?
                ResponseEntity.status(HttpStatus.OK).body(deleted) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
    
    // 트랜잭션 -> 실패 -> 롤백
    @PostMapping("/api/transaction-test")
    public ResponseEntity<List<Article>> transactionTest(@RequestBody List<ArticleFormDto> requestDtos){
        List<Article> createdList = articleService.createArticles(requestDtos);

        return createdList != null ?
                ResponseEntity.status(HttpStatus.OK).body(createdList) :
                ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }
}
