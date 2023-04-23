package com.example.boardservice.repository;

import com.example.boardservice.config.JpaConfig;
import com.example.boardservice.domain.Article;
import com.example.boardservice.domain.ArticleComment;
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

    public JpaRepositoryTest(
            @Autowired ArticleRepository articleRepository,
            @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
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
        //When
        Article article = articleRepository.save(Article.of("new title","new content", "new hashtag"));
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