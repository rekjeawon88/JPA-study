package com.study.jpa.repository;

import com.study.jpa.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BookRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BookRepository bookRepository;

    @Test
    void findByAuthor() {
        // given
        Book book1 = Book.builder()
                .title("스프링 부트 가이드")
                .author("홍길동")
                .description("스프링 부트 학습서")
                .price(30000)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Book book2 = Book.builder()
                .title("JPA 프로그래밍")
                .author("홍길동")
                .description("JPA 학습서")
                .price(28000)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Book book3 = Book.builder()
                .title("자바 기초")
                .author("김철수")
                .description("자바 입문서")
                .price(25000)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        entityManager.persist(book1);
        entityManager.persist(book2);
        entityManager.persist(book3);
        entityManager.flush();

        // when
        List<Book> books = bookRepository.findByAuthor("홍길동");

        // then
        assertEquals(2, books.size());
        assertTrue(books.stream().allMatch(book -> "홍길동".equals(book.getAuthor())));
    }

    @Test
    void findByTitleContaining() {
        // given
        Book book1 = Book.builder()
                .title("스프링 부트 가이드")
                .author("홍길동")
                .description("스프링 부트 학습서")
                .price(30000)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Book book2 = Book.builder()
                .title("스프링 프레임워크")
                .author("김철수")
                .description("스프링 프레임워크 학습서")
                .price(28000)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Book book3 = Book.builder()
                .title("자바 기초")
                .author("이영희")
                .description("자바 입문서")
                .price(25000)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        entityManager.persist(book1);
        entityManager.persist(book2);
        entityManager.persist(book3);
        entityManager.flush();

        // when
        List<Book> books = bookRepository.findByTitleContaining("스프링");

        // then
        assertEquals(2, books.size());
        assertTrue(books.stream().allMatch(book -> book.getTitle().contains("스프링")));
    }

    @Test
    void findBooksLessThanPrice() {
        // given
        Book book1 = Book.builder()
                .title("스프링 부트 가이드")
                .author("홍길동")
                .description("스프링 부트 학습서")
                .price(30000)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Book book2 = Book.builder()
                .title("JPA 프로그래밍")
                .author("김철수")
                .description("JPA 학습서")
                .price(28000)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        Book book3 = Book.builder()
                .title("자바 기초")
                .author("이영희")
                .description("자바 입문서")
                .price(25000)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        entityManager.persist(book1);
        entityManager.persist(book2);
        entityManager.persist(book3);
        entityManager.flush();

        // when
        List<Book> books = bookRepository.findBooksLessThanPrice(29000);

        // then
        assertEquals(2, books.size());
        assertTrue(books.stream().allMatch(book -> book.getPrice() < 29000));
    }
}
