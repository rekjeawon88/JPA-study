package com.study.jpa.controller;

import com.study.jpa.entity.Book;
import com.study.jpa.service.BookService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/books")
@RequiredArgsConstructor
public class BookWebController {

    private final BookService bookService;

    @GetMapping
    public String getAllBooks(Model model) {
        model.addAttribute("books", bookService.getAllBooks());
        return "book-list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("book", new Book());
        return "book-form";
    }

    @PostMapping
    public String createBook(@Valid @ModelAttribute("book") Book book, BindingResult result) {
        if (result.hasErrors()) {
            return "book-form";
        }
        bookService.createBook(book);
        return "redirect:/books";
    }

    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Book book = bookService.getBookById(id)
                .orElseThrow(() -> new IllegalArgumentException("Invalid book Id:" + id));
        model.addAttribute("book", book);
        return "book-form";
    }

    @PostMapping("/update/{id}")
    public String updateBook(@PathVariable Long id, @Valid @ModelAttribute("book") Book book,
                             BindingResult result) {
        if (result.hasErrors()) {
            return "book-form";
        }
        bookService.updateBook(id, book);
        return "redirect:/books";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) {
        bookService.deleteBook(id);
        return "redirect:/books";
    }

    @GetMapping("/search")
    public String searchBooks(@RequestParam String keyword, Model model) {
        model.addAttribute("books", bookService.searchBooksByTitle(keyword));
        model.addAttribute("keyword", keyword);
        return "book-list";
    }
}
