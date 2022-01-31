package com.example.springbootpractice.repository;

import com.example.springbootpractice.entity.Article;
import com.example.springbootpractice.entity.Comment;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest // JPA와 연동한 테스트임을 지정
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @Autowired
    private ArticleRepository articleRepository;
    
    @Test
    @DisplayName("특정 게시글의 모든 댓글 조회") //테스트 결과에 보여줄 이름
    void findByArticleId() {
        /* Case 1: 4번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 4L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = new Article(4L, "당신의 인생 영화는 ?", "댓글 ㄱ");
            Comment a =  new Comment(1L, article, "Park", "굳 윌 헌팅");
            Comment b =  new Comment(2L, article, "Kim", "아이 엠 샘");
            Comment c =  new Comment(3L, article, "Choi", "쇼생크의 탈출");

            List<Comment> expected = Arrays.asList(a,b,c);
            // 검증
            assertEquals(expected.toString(), comments.toString(), "4번 글의 모든 댓글을 출력");
        }

        /* Case 2: 1번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 1L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            Article article = new Article(1L, "가가가가", "1111");
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "1번 글은 댓글이 없음");
        }

        /* Case 3: 9번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 9L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId); //원소가 하나도 없는 List가 반환됨

            // 예상하기
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "9번 게시글은 존재하지 않음");
        }

        /* Case 4: 9999번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = 9999L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "9999번 게시글은 존재하지 않음");
        }

        /* Case 5: -1번 게시글의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            Long articleId = -1L;

            // 실제 수행
            List<Comment> comments = commentRepository.findByArticleId(articleId);

            // 예상하기
            List<Comment> expected = Arrays.asList();

            // 검증
            assertEquals(expected.toString(), comments.toString(), "-1번 게시글은 존재하지 않음");
        }
    }

    @Test
    @DisplayName("특정 닉네임의 모든 댓글 조회")
    void findByNickname() {
        /* Case 1: "Park"의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = "Park";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상하기
            Comment a = Comment.builder().
                    id(1L).
                    nickname(nickname).
                    body("굳 윌 헌팅")
                    .article(new Article(4L, "당신의 인생 영화는 ?", "댓글 ㄱ")).build();

            Comment b = Comment.builder().
                    id(4L).
                    nickname(nickname).
                    body("치킨").
                    article(new Article(5L, "당신의 소울 푸드는 ?", "댓글 ㄱㄱ")).build();

            Comment c = Comment.builder().id(7L).
                    nickname(nickname).
                    body("조깅").
                    article(new Article(6L, "당신의 취미는 ?", "댓글 ㄱㄱㄱ")).build();

            List<Comment> expected = Arrays.asList(a,b,c);
            // 검증
            assertEquals(expected.toString(), comments.toString(), "Park의 모든 댓글을 출력");
        }

        /* Case 2: "Kim"의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = "Kim";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상
            Comment a = Comment.builder()
                    .id(2L)
                    .nickname(nickname)
                    .article(new Article(4L, "당신의 인생 영화는 ?", "댓글 ㄱ"))
                    .body("아이 엠 샘")
                    .build();

            Comment b = Comment.builder()
                    .id(5L)
                    .nickname(nickname)
                    .article(new Article(5L, "당신의 소울 푸드는 ?", "댓글 ㄱㄱ"))
                    .body("샤브샤브")
                    .build();

            Comment c = Comment.builder()
                    .id(8L)
                    .nickname(nickname)
                    .article(new Article(6L, "당신의 취미는 ?", "댓글 ㄱㄱㄱ"))
                    .body("유튜브").build();

            List<Comment> expected = Arrays.asList(a, b, c);
            
            // 검증
            assertEquals(expected.toString(), comments.toString());
        }
        /* Case 3: null의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = null;

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상
            List<Comment> expected = new ArrayList<>();

            // 검증
            assertEquals(expected.toString(), comments.toString());
        }

        /* Case 4: {}(공백) 의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            String nickname = "";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);

            // 예상
            List<Comment> expected = new ArrayList<>();

            // 검증
            assertEquals(expected.toString(), comments.toString());
        }

        /* Case 5: "i"의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            /*
             * SELECT * FROM COMMENT WHERE REGEXP_LIKE(nickname, '^\w*[i]\w*$');
             * 해당 쿼리를 이용하면 닉네임에 i가 들어간 사용자의 댓글만 가져올 수 있다.
             */
            String nickname = "i";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNickname(nickname);
            //List<Comment> comments = commentRepository.findByNicknameRegex("^\\w*[i]\\w*$");

            // 예상
            List<Comment> expected = new ArrayList<>();

            // 검증
            assertEquals(expected.toString(), comments.toString());
        }

        /* Case 6: 닉네임에 "i"가 들어간 사용자의 모든 댓글 조회 */
        {
            // 입력 데이터 준비
            /*
             * SELECT * FROM COMMENT WHERE REGEXP_LIKE(nickname, '^\w*[i]\w*$');
             * 해당 쿼리를 이용하면 닉네임에 i가 들어간 사용자의 댓글만 가져올 수 있다.
             */
            String regex = "^\\w*[i]\\w*$";

            // 실제 수행
            List<Comment> comments = commentRepository.findByNicknameRegex(regex);

            // 예상
            Comment a = Comment.builder()
                    .id(2L)
                    .nickname("Kim")
                    .article(new Article(4L, "당신의 인생 영화는 ?", "댓글 ㄱ"))
                    .body("아이 엠 샘").build();
            
            Comment b = Comment.builder()
                    .id(3L)
                    .nickname("Choi")
                    .article(new Article(4L, "당신의 인생 영화는 ?", "댓글 ㄱ"))
                    .body("쇼생크의 탈출").build();
            
            Comment c = Comment.builder()
                    .id(5L)
                    .nickname("Kim")
                    .article(new Article(5L, "당신의 소울 푸드는 ?", "댓글 ㄱㄱ"))
                    .body("샤브샤브").build();
            
            Comment d = Comment.builder()
                    .id(6L)
                    .nickname("Choi")
                    .article(new Article(5L, "당신의 소울 푸드는 ?", "댓글 ㄱㄱ"))
                    .body("초밥").build();
            
            Comment e = Comment.builder()
                    .id(8L)
                    .nickname("Kim")
                    .article(new Article(6L, "당신의 취미는 ?", "댓글 ㄱㄱㄱ"))
                    .body("유튜브").build();
            
            Comment f = Comment.builder()
                    .id(9L)
                    .nickname("Choi")
                    .article(new Article(6L, "당신의 취미는 ?", "댓글 ㄱㄱㄱ"))
                    .body("독서").build();
            

            List<Comment> expected = Arrays.asList(a,b,c,d,e,f);

            // 검증
            assertEquals(expected.toString(), comments.toString());
        }
    }

    @Test
    void JPA를_활용한_댓글과_아티클_조회() {
        // 준비
        System.out.println("------------------------- 댓글 조회 -------------------------------");
        Comment comment = commentRepository.findById(1L).orElse(null); 
        // article_id를 기준으로 left outer join이 됨
        System.out.println(comment);
        
        // 수행
        System.out.println("------------------------- 댓글에서 게시글 가져오기 -------------------------------");
        Article article = comment.getArticle();
        System.out.println(article);

        // 예상
        System.out.println("------------------------- 게시글 조회 -------------------------------");
        Article expected = articleRepository.findById(4L).orElse(null);
        //articleRepository의 쿼리는 안나오는 이유 ?
        
        // 검증
        assertEquals(expected.toString(), article.toString());
    }
}