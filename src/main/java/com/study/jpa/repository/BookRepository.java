package com.study.jpa.repository;

import com.study.jpa.entity.Book;
import jakarta.persistence.QueryHint;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    // 기본 CRUD 메서드는 JpaRepository에서 제공

    // 커스텀 쿼리 메서드
    List<Book> findByAuthor(String author);

    List<Book> findByTitleContaining(String title);

    // JPQL 쿼리 예제
    @Query("SELECT b FROM Book b WHERE b.price < ?1")
    List<Book> findBooksLessThanPrice(int price);

}
