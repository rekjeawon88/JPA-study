package com.study.jpa;

import com.study.jpa.entity.Book;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class BookApiApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Test
	void contextLoads() {
	}

	@Test
	void crudTest() throws Exception {
		// 1. 도서 생성
		Book newBook = Book.builder()
				.title("통합 테스트 도서")
				.author("테스트 저자")
				.description("통합 테스트용 도서")
				.price(27000)
				.build();

		MvcResult postResult = mockMvc.perform(post("/api/books")
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(newBook)))
				.andExpect(status().isCreated())
				.andExpect(jsonPath("$.title", is("통합 테스트 도서")))
				.andReturn();

		// 생성된 도서의 ID 추출
		String responseContent = postResult.getResponse().getContentAsString();
		Book createdBook = objectMapper.readValue(responseContent, Book.class);
		Long bookId = createdBook.getId();

		// 2. 도서 조회
		mockMvc.perform(get("/api/books/" + bookId))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is(bookId.intValue())))
				.andExpect(jsonPath("$.title", is("통합 테스트 도서")));

		// 3. 도서 수정
		Book updatedBook = Book.builder()
				.title("수정된 통합 테스트 도서")
				.author("테스트 저자")
				.description("수정된 통합 테스트용 도서")
				.price(29000)
				.build();

		mockMvc.perform(put("/api/books/" + bookId)
						.contentType(MediaType.APPLICATION_JSON)
						.content(objectMapper.writeValueAsString(updatedBook)))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.title", is("수정된 통합 테스트 도서")))
				.andExpect(jsonPath("$.price", is(29000)));

		// 4. 도서 삭제
		mockMvc.perform(delete("/api/books/" + bookId))
				.andExpect(status().isNoContent());

		// 5. 삭제 확인
		mockMvc.perform(get("/api/books/" + bookId))
				.andExpect(status().isNotFound());
	}
}
