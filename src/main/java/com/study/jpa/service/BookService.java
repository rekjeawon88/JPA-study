package com.study.jpa.service;

import com.study.jpa.entity.Book;
import com.study.jpa.repository.BookRepository;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BookService {

    private final BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Optional<Book> getBookById(Long id) {
        return bookRepository.findById(id);
    }

    public List<Book> getBooksByAuthor(String author) {
        return bookRepository.findByAuthor(author);
    }

    public List<Book> searchBooksByTitle(String keyword) {
        return bookRepository.findByTitleContaining(keyword);
    }

    public List<Book> getBooksLessThanPrice(int price) {
        return bookRepository.findBooksLessThanPrice(price);
    }

    @Transactional
    public Book createBook(Book book) {
        book.setCreatedAt(LocalDateTime.now());
        book.setUpdatedAt(LocalDateTime.now());
        return bookRepository.save(book);
    }

    @Transactional
    public Optional<Book> updateBook(Long id, Book bookDetails) {
        return bookRepository.findById(id)
                .map(existingBook -> {
                    existingBook.setTitle(bookDetails.getTitle());
                    existingBook.setAuthor(bookDetails.getAuthor());
                    existingBook.setPrice(bookDetails.getPrice());
                    existingBook.setDescription(bookDetails.getDescription());
                    existingBook.setUpdatedAt(LocalDateTime.now());
                    return bookRepository.save(existingBook);
                });
    }

    @Transactional
    public boolean deleteBook(Long id) {
        return bookRepository.findById(id)
                .map(book -> {
                    bookRepository.delete(book);
                    return true;
                })
                .orElse(false);
    }

}
