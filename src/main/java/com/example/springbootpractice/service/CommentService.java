package com.example.springbootpractice.service;

import com.example.springbootpractice.dto.CommentDto;
import com.example.springbootpractice.entity.Article;
import com.example.springbootpractice.entity.Comment;
import com.example.springbootpractice.repository.ArticleRepository;
import com.example.springbootpractice.repository.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
public class CommentService {
    
    /*
     * Comment도 가져와야 하지만 Article도 가져와야 하므로 하나의 서비스에서 같이 쓸 수 있도록 함
     */
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ArticleRepository articleRepository;

    public List<CommentDto> comments(Long article_id) {
        // 조회 : 댓글 목록
        //List<Comment> comments = commentRepository.findByArticleId(article_id);

        // 변환 : 엔티티 -> Dto
        /*List<CommentDto> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto dto = CommentDto.createCommentDto(comment);
            dtos.add(dto);
        }*/

        // 반환 (람다식을 사용하여 한 번에 구현)
        return commentRepository.findByArticleId(article_id)
                .stream()
                .map(comment -> CommentDto.createCommentDto(comment))
                .collect(Collectors.toList());
    }

    @Transactional // DB의 내용이 변경 될 수 있으므로 트랜잭션을 붙여주어 문제가 없을 경우에만 반영하도록 함
    public CommentDto create(Long article_id, CommentDto requestDto){

        // 게시글 조회 및 예외 발생
        Article article = articleRepository.findById(article_id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 생성 실패 : 대상 게시글이 없습니다. id = " + article_id));

        // 댓글 엔티티 생성
        
        // Entity 변환과 동시에 예외 처리 등 추가적인 기능을 추가하기 위해 toEntity() 대신 새 메소드를 작성함
        //Dto에서 toEntity를 만드는 대신 Comment에서 dto를 Entity로 변경하면서 예외 처리까지 같이 함
        Comment comment = Comment.createComment(requestDto, article); 

        // 댓글 엔티티를 DB로 저장
        Comment created = commentRepository.save(comment);

        // Dto로 변경하여 반환
         return CommentDto.createCommentDto(created);
    }

    @Transactional //데이터를 건드리므로 @Transactinal 추가
    public CommentDto update(Long id, CommentDto requestDto) {
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("댓글 수정 실패 : 대상 댓글이 없습니다."));
        // 댓글 수정
        target.patch(requestDto);

        // DB로 갱신
        Comment updated = commentRepository.save(target);

        // 댓글 엔티티를 Dto로 변환 및 반환
        return CommentDto.createCommentDto(updated);
    }

    @Transactional
    public CommentDto delete(Long id) {
        // 댓글 조회 및 예외 발생
        Comment target = commentRepository.findById(id)
                .orElseThrow(()->new IllegalArgumentException("댓글 삭제 실패 : 대상이 없습니다."));
        // 댓글 삭제
        commentRepository.delete(target);

        // 삭제 댓글을 Dto로 반환
        return CommentDto.createCommentDto(target);
    }
}
