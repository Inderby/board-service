package com.example.boardservice.repository;

import com.example.boardservice.config.JpaConfig;
import com.example.boardservice.domain.Article;
import com.fastcampus.projectboard.domain.UserAccount;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.*;

//@ActiveProfiles("testdb")
@DisplayName("JPA 연결 테스트")
@Import(JpaConfig.class)
@DataJpaTest
class JpaRepositoryTest {
    private final ArticleRepository articleRepository;
    private final ArticleCommentRepository articleCommentRepository;
    private final UserAccountRepository userAccountRepository;

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository,
            @Autowired UserAccountRepository userAccountRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @DisplayName("select 테스트")
    @Test
    void givenTestData_whenSelecting_whenWorksFine(){
        //Given

        //When
        List<Article> articles =  articleRepository.findAll();
        //Then
        assertThat(articles)
                .isNotNull()
                .hasSize(1000);
    }
    @DisplayName("insert 테스트")
    @Test
    void givenTestData_whenInserting_whenWorksFine(){
        //Given
        long previousCount = articleRepository.count();
        UserAccount userAccount = userAccountRepository.save(UserAccount.of("inderby", "pw", null,null,null));
        //When
        Article article = articleRepository.save(Article.of(userAccount,"new title","new content", "new hashtag"));
        //Then
        assertThat(articleRepository.count()).isEqualTo(previousCount+1);
    }
    @DisplayName("update 테스트")
    @Test
    void givenTestData_whenUpdating_whenWorksFine(){
        //Given
        Article article = articleRepository.findById(1L).orElseThrow();
        String updateHashTag = "#springboot";
        article.setHashtag(updateHashTag);
        //When
        Article savedArticle = articleRepository.saveAndFlush(article);
        //Then
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updateHashTag);
    }
}