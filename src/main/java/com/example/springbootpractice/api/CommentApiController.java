package com.example.springbootpractice.api;


import com.example.springbootpractice.dto.CommentDto;
import com.example.springbootpractice.entity.Comment;
import com.example.springbootpractice.service.CommentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class CommentApiController {

    @Autowired
    private CommentService commentService;
    
    // 댓글 목록 조회
    @GetMapping("/api/articles/{article_id}/comments")
    public ResponseEntity<List<CommentDto>> comments(@PathVariable Long article_id){
        // 서비스에게 조회 요청
        List<CommentDto> dtos = commentService.comments(article_id);

        // 결과를 응답 코드와 함께 리턴
        return ResponseEntity.status(HttpStatus.OK).body(dtos);
    }

    // 댓글 생성
    @PostMapping("/api/articles/{article_id}/comments")
    public ResponseEntity<CommentDto> createComment(@PathVariable Long article_id, @RequestBody CommentDto requestDto){
        // 서비스에게 등록 요청
        CommentDto createdDto = commentService.create(article_id, requestDto);

        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(createdDto);
    }


    // 댓글 수정
    @PatchMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id, @RequestBody CommentDto requestDto){
        // 서비스에게 수정 요청
        CommentDto updatedDto = commentService.update(id, requestDto);

        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(updatedDto);
    }
    // 댓글 삭제
    @DeleteMapping("/api/comments/{id}")
    public ResponseEntity<CommentDto> updateComment(@PathVariable Long id){
        // 서비스에게 수정 요청
        CommentDto deletedDto = commentService.delete(id);

        //결과 응답
        return ResponseEntity.status(HttpStatus.OK).body(deletedDto);
    }
}
