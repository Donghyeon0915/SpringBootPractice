package com.example.springbootpractice.repository;

import com.example.springbootpractice.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

//<관리 대상, 관리 대상의 id 타입>
/*
 * JpaRepository : PagingAndSortingRepository를 확장한 Repository
 * 페이지 처리(Paging)도 할 수 있고 정렬(Sorting)도 할 수 있는 Repository
 */
public interface CommentRepository extends JpaRepository<Comment, Long> {

    // 특정 게시글의 모든 댓글 조회
    @Query(value ="SELECT * FROM COMMENT WHERE article_id = :articleId", nativeQuery = true)
    List<Comment> findByArticleId(Long articleId); // SQL문 안에서 :articleId와 매핑됨(이름 똑같아야함)

    // 특정 닉네임의 모든 댓글 조회
    //@Query(value = "SELECT * FROM COMMENT WHERE nickname = :nickname", nativeQuery = true) -> xml로 작성했음
    List<Comment> findByNickname(String nickname);

    // 특정 닉네임의 모든 댓글을 정규표현식을 이용하여 조회
    @Query(value = "SELECT * FROM COMMENT WHERE REGEXP_LIKE(nickname, :regex)", nativeQuery = true)
    List<Comment> findByNicknameRegex(String regex);
}
