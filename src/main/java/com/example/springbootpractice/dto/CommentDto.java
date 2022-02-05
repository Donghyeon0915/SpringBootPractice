package com.example.springbootpractice.dto;


import com.example.springbootpractice.entity.Article;
import com.example.springbootpractice.entity.Comment;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CommentDto {
    private Long id;
    /*
     * JSON 요청은 Dto로 받기 때문에 JSON의 필드와 Dto의 필드 이름을 일치시켜 줘야함
     * 해당 변수는 JSON에서 "article_id"로 넘어온다는 것을 명시
     */
    @JsonProperty("article_id")
    private Long articleId; // 댓글이 포함 된 게시글의 아이디
    private String nickname; // 작성자 닉네임
    private String body;

    public static CommentDto createCommentDto(Comment comment){
        return CommentDto.builder()
                .id(comment.getId())
                .articleId(comment.getArticle().getId())
                .nickname(comment.getNickname())
                .body(comment.getBody())
                .build();
    }

    public Comment toEntity(Article article){
        return Comment.builder()
                .id(this.id)
                .nickname(this.nickname)
                .body(this.body)
                .article(article)
                .build();
    }
}
