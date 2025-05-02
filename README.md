# 📚 Spring Boot REST API & JPA 도서 관리 시스템

Spring Boot, JPA, REST API를 학습하기 위한 도서 관리 시스템 프로젝트입니다.

## 🔍 프로젝트 개요

이 프로젝트는 도서 정보를 관리하는 웹 애플리케이션으로, Spring Boot 기반의 백엔드와 Thymeleaf 템플릿 엔진을 활용한 프론트엔드로 구성되어 있습니다.

## 🛠️ 기술 스택

- **Backend**
  - Java 21
  - Spring Boot
  - Spring Data JPA
  - Lombok
  - H2 Database (개발용)
  
- **Frontend**
  - Thymeleaf
  - Bootstrap

## 📋 주요 기능

- 도서 목록 조회, 상세 조회, 등록, 수정, 삭제
- RESTful API 제공
- 웹 인터페이스를 통한 도서 관리
- JPA를 활용한 데이터베이스 연동

## 🏗️ 프로젝트 구조

```
src
├── main
│   ├── java
│   │   └── com
│   │       └── example
│   │           └── bookapi
│   │               ├── BookApiApplication.java
│   │               ├── controller
│   │               │   ├── BookApiController.java
│   │               │   └── BookWebController.java
│   │               ├── model
│   │               │   └── Book.java
│   │               ├── repository
│   │               │   └── BookRepository.java
│   │               └── service
│   │                   └── BookService.java
│   └── resources
│       ├── application.properties
│       ├── static
│       │   └── css
│       │       └── style.css
│       └── templates
│           ├── book-form.html
│           ├── book-list.html
│           └── book-view.html
└── test
    └── java
        └── com
            └── example
                └── bookapi
                    └── BookApiApplicationTests.java
```

## 🔄 컴포넌트 설명

- **모델(Model)**: `Book` 엔티티 클래스
- **리포지토리(Repository)**: JPA를 활용한 `BookRepository` 인터페이스
- **서비스(Service)**: 비즈니스 로직을 담당하는 `BookService` 클래스
- **컨트롤러(Controller)**:
  - REST API를 제공하는 `BookApiController`
  - 웹 인터페이스를 제공하는 `BookWebController`
- **뷰(View)**: Thymeleaf 템플릿을 사용한 웹 페이지

## 📡 REST API 엔드포인트

| 메서드 | URL | 설명 |
|-----|-----|-----|
| GET | /api/books | 모든 도서 조회 |
| GET | /api/books/{id} | ID로 도서 조회 |
| GET | /api/books/author/{author} | 저자로 도서 조회 |
| GET | /api/books/search?title={keyword} | 제목으로 도서 검색 |
| GET | /api/books/price/{price} | 특정 가격 이하 도서 조회 |
| POST | /api/books | 새 도서 등록 |
| PUT | /api/books/{id} | 도서 정보 수정 |
| DELETE | /api/books/{id} | 도서 삭제 |

## 🚀 실행 방법

### 사전 요구사항
- JDK 21 이상
- Gradle 7.x 이상
- IntelliJ IDEA (권장) 또는 다른 Java IDE

### 실행 단계
1. 레포지토리 클론
   ```bash
   git clone https://github.com/yourusername/book-management-system.git
   cd book-management-system
   ```

2. 애플리케이션 빌드
   ```bash
   ./gradlew build
   ```

3. 애플리케이션 실행
   ```bash
   ./gradlew bootRun
   ```
   또는 IDE에서 `BookApiApplication` 클래스 실행

4. 웹 브라우저에서 접속
   ```
   http://localhost:8080/books
   ```

## 📝 학습 포인트

### JPA 학습 요소
- **엔티티 정의**: `@Entity`, `@Id`, `@GeneratedValue` 등의 애노테이션 사용
- **리포지토리 인터페이스**: `JpaRepository` 상속을 통한 기본 CRUD 메서드 제공
- **쿼리 메서드**: 메서드 이름으로 쿼리 생성 (예: `findByAuthor`, `findByTitleContaining`)
- **JPQL 쿼리**: `@Query` 애노테이션을 사용한 커스텀 쿼리 작성
- **트랜잭션 관리**: `@Transactional` 애노테이션을 통한 트랜잭션 관리

### REST API 학습 요소
- **컨트롤러 애노테이션**: `@RestController`, `@RequestMapping` 등 사용
- **HTTP 메서드 매핑**: `@GetMapping`, `@PostMapping`, `@PutMapping`, `@DeleteMapping` 사용
- **경로 변수**: `@PathVariable`을 통한 URL 경로 변수 추출
- **요청 파라미터**: `@RequestParam`을 통한 쿼리 파라미터 추출
- **요청 본문**: `@RequestBody`를 통한 JSON 요청 본문 매핑
- **응답 상태 코드**: `ResponseEntity`를 통한 HTTP 상태 코드 및 응답 본문 제어

## 📄 라이센스

MIT License

## 👥 기여 방법

1. 이 레포지토리를 포크합니다.
2. 새 기능 브랜치를 생성합니다 (`git checkout -b feature/amazing-feature`)
3. 변경사항을 커밋합니다 (`git commit -m 'Add some amazing feature'`)
4. 브랜치에 푸시합니다 (`git push origin feature/amazing-feature`)
5. Pull Request를 생성합니다.
