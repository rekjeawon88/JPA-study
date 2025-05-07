package com.study.jpa.config;

import com.study.jpa.entity.Book;
import com.study.jpa.repository.BookRepository;
import java.time.LocalDateTime;
import java.util.Arrays;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BookRepository bookRepository;

    @Override
    public void run(String... args) {
        // 초기 데이터 생성
        if (bookRepository.count() == 0) {
            LocalDateTime now = LocalDateTime.now();

            Book book1 = Book.builder()
                    .title("스프링 부트 실전 가이드")
                    .author("홍길동")
                    .description("스프링 부트를 활용한 웹 애플리케이션 개발 가이드")
                    .price(30000)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();

            Book book2 = Book.builder()
                    .title("JPA 프로그래밍")
                    .author("김철수")
                    .description("JPA를 활용한 데이터베이스 프로그래밍")
                    .price(28000)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();

            Book book3 = Book.builder()
                    .title("REST API 디자인 패턴")
                    .author("이영희")
                    .description("효율적인 REST API 설계 방법")
                    .price(25000)
                    .createdAt(now)
                    .updatedAt(now)
                    .build();

            bookRepository.saveAll(Arrays.asList(book1, book2, book3));
        }
    }
}
