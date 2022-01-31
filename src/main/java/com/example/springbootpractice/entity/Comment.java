package com.example.springbootpractice.entity;


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
}
