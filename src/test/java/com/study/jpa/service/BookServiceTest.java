package com.study.jpa.service;

import com.study.jpa.entity.Book;
import com.study.jpa.repository.BookRepository;
import com.study.jpa.entity.Book;
import com.study.jpa.repository.BookRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;

    private Book testBook;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        testBook = Book.builder()
                .id(1L)
                .title("테스트 도서")
                .author("테스트 저자")
                .description("테스트 설명")
                .price(25000)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    @Test
    void getAllBooks() {
        // given
        List<Book> books = Arrays.asList(testBook);
        when(bookRepository.findAll()).thenReturn(books);

        // when
        List<Book> result = bookService.getAllBooks();

        // then
        assertEquals(1, result.size());
        assertEquals("테스트 도서", result.get(0).getTitle());
        verify(bookRepository, times(1)).findAll();
    }

    @Test
    void getBookById() {
        // given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));

        // when
        Optional<Book> result = bookService.getBookById(1L);

        // then
        assertTrue(result.isPresent());
        assertEquals("테스트 도서", result.get().getTitle());
        verify(bookRepository, times(1)).findById(1L);
    }

    @Test
    void createBook() {
        // given
        Book newBook = Book.builder()
                .title("새 도서")
                .author("새 저자")
                .description("새 설명")
                .price(30000)
                .build();

        Book savedBook = Book.builder()
                .id(2L)
                .title("새 도서")
                .author("새 저자")
                .description("새 설명")
                .price(30000)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();

        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        // when
        Book result = bookService.createBook(newBook);

        // then
        assertNotNull(result);
        assertEquals(2L, result.getId());
        assertEquals("새 도서", result.getTitle());
        assertNotNull(result.getCreatedAt());
        assertNotNull(result.getUpdatedAt());
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void updateBook() {
        // given
        Book updatedBook = Book.builder()
                .title("수정된 도서")
                .author("수정된 저자")
                .description("수정된 설명")
                .price(35000)
                .build();

        Book existingBook = Book.builder()
                .id(1L)
                .title("테스트 도서")
                .author("테스트 저자")
                .description("테스트 설명")
                .price(25000)
                .createdAt(LocalDateTime.now().minusDays(1))
                .updatedAt(LocalDateTime.now().minusDays(1))
                .build();

        Book savedBook = Book.builder()
                .id(1L)
                .title("수정된 도서")
                .author("수정된 저자")
                .description("수정된 설명")
                .price(35000)
                .createdAt(existingBook.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        when(bookRepository.findById(1L)).thenReturn(Optional.of(existingBook));
        when(bookRepository.save(any(Book.class))).thenReturn(savedBook);

        // when
        Optional<Book> result = bookService.updateBook(1L, updatedBook);

        // then
        assertTrue(result.isPresent());
        assertEquals("수정된 도서", result.get().getTitle());
        assertEquals(35000, result.get().getPrice());
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).save(any(Book.class));
    }

    @Test
    void deleteBook() {
        // given
        when(bookRepository.findById(1L)).thenReturn(Optional.of(testBook));
        doNothing().when(bookRepository).delete(any(Book.class));

        // when
        boolean result = bookService.deleteBook(1L);

        // then
        assertTrue(result);
        verify(bookRepository, times(1)).findById(1L);
        verify(bookRepository, times(1)).delete(any(Book.class));
    }
}
