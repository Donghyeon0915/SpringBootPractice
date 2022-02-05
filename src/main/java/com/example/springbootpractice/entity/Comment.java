package com.example.springbootpractice.entity;


import com.example.springbootpractice.dto.CommentDto;
import lombok.*;

import javax.persistence.*;

@ToString
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Comment {

    @Id //PK (Primary Key : 자기 자신의 아이디)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne //여러 개의 Comment가 하나의 Article에 달리기 때문에 다대일 관계
    @JoinColumn(name = "article_id") //FK (가리키는 상대방의 아이디) 설정 (연결 대상을 가리키는 컬럼 이름), DB에서의 포인터 느낌, "article_id" 이름으로 컬럼이 만들어짐
    private Article article; // 해당 컬럼과 Article의 PK가 연결되어있다는 뜻

    @Column
    private String nickname;

    @Column
    private String body;

    public static Comment createComment(CommentDto requestDto, Article article){
        //댓글 생성 시엔 id를 입력받지 않아야함(DB가 자동 생성해야 하므로)
        if(requestDto.getId() != null) throw new IllegalArgumentException("댓글 생성 실패 : 댓글의 아이디가 없어야합니다.");

        //URL의 article_id와 요청으로 들어온 JSON 데이터의 article_id가 다르면 에러
        if(requestDto.getArticleId() != article.getId())
            throw new IllegalArgumentException("댓글 생성 실패 : 게시글의 아이디가 잘못되었습니다.");

        //엔티티 생성 및 반환
        return Comment.builder()
                .id(requestDto.getId())
                .article(article)
                .nickname(requestDto.getNickname())
                .body(requestDto.getBody())
                .build();
    }

    public void patch(CommentDto requestDto) {
        // 예외 발생
        if(this.id != requestDto.getId())
            throw new IllegalArgumentException("댓글 수정 실패 : 잘못된 아이디가 입력되었습니다.");

        // 객체를 갱신
        if(requestDto.getNickname() != null)
            this.nickname = requestDto.getNickname();
        if(requestDto.getBody() != null)
            this.body = requestDto.getBody();
    }
}
