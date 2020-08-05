package com.csg.cms.persistence;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

import com.csg.cms.domain.Article;
import com.csg.cms.enumerate.Status;
import com.csg.cms.enumerate.Type;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@Transactional
@DisplayName("演示通过EntityManager操作JPA持久化Enum类型数据")
public class EnumJPATest {

    @PersistenceContext
    private EntityManager em;

    @Test
    void entityManagerIsNotEmpty(){
        assertNotNull(em, "EntityManager is not null");
    }

    @Test
    @DisplayName("可以保存Enum普通类型到 数据库")
    void persistEnumTypeStringValue(){
        // given
        Article article = new Article();
        article.setId(1);
        article.setTitle("ordinal title");
        article.setStatus(Status.OPEN);

        em.persist(article);
        em.flush();
        // then
        Article persistedArticle = em.find(Article.class, 1);

        assertEquals(1, persistedArticle.getId());
        assertEquals("ordinal title", persistedArticle.getTitle());
        assertEquals(Status.OPEN, persistedArticle.getStatus());
    }

    @Test
    @DisplayName("可以保存Enum字符串类型到数据库")
    @Transactional
    void persistEnumIntValue(){
        // given
        Article article = new Article();
        article.setId(2);
        article.setTitle("string title");
        article.setType(Type.EXTERNAL);

        // when
        em.persist(article);

        // then
        Article persistedArticle = em.find(Article.class, 2);

        assertEquals(2, persistedArticle.getId());
        assertEquals("string title", persistedArticle.getTitle());
        assertEquals(Type.EXTERNAL, persistedArticle.getType());
    }
}