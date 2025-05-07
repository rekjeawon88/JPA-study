package com.study.jpa.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.jpa.entity.Book;
import com.study.jpa.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(BookApiController.class)
class BookApiControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BookService bookService;

    @Autowired
    private ObjectMapper objectMapper;

    private Book testBook;

    @BeforeEach
    void setUp() {
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
    void getAllBooks() throws Exception {
        when(bookService.getAllBooks()).thenReturn(Arrays.asList(testBook));

        mockMvc.perform(get("/api/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].title", is("테스트 도서")))
                .andExpect(jsonPath("$[0].author", is("테스트 저자")));
    }

    @Test
    void getBookById() throws Exception {
        when(bookService.getBookById(1L)).thenReturn(Optional.of(testBook));

        mockMvc.perform(get("/api/books/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("테스트 도서")));
    }

    @Test
    void getBookByIdNotFound() throws Exception {
        when(bookService.getBookById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/books/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void createBook() throws Exception {
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

        when(bookService.createBook(any(Book.class))).thenReturn(savedBook);

        mockMvc.perform(post("/api/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newBook)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(2)))
                .andExpect(jsonPath("$.title", is("새 도서")));
    }

    @Test
    void updateBook() throws Exception {
        Book updatedBook = Book.builder()
                .title("수정된 도서")
                .author("수정된 저자")
                .description("수정된 설명")
                .price(35000)
                .build();

        Book savedBook = Book.builder()
                .id(1L)
                .title("수정된 도서")
                .author("수정된 저자")
                .description("수정된 설명")
                .price(35000)
                .createdAt(testBook.getCreatedAt())
                .updatedAt(LocalDateTime.now())
                .build();

        when(bookService.updateBook(anyLong(), any(Book.class))).thenReturn(Optional.of(savedBook));

        mockMvc.perform(put("/api/books/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatedBook)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("수정된 도서")))
                .andExpect(jsonPath("$.price", is(35000)));
    }

    @Test
    void deleteBook() throws Exception {
        when(bookService.deleteBook(1L)).thenReturn(true);

        mockMvc.perform(delete("/api/books/1"))
                .andExpect(status().isNoContent());
    }
}
