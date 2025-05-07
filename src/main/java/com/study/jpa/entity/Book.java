package com.study.jpa.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "제목은 필수입니다")
    private String title;

    @NotBlank(message = "저자는 필수입니다")
    private String author;

    @NotNull(message = "가격은 필수입니다")
    private Integer price;

    private String description;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
