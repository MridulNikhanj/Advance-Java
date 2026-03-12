package com.example.librarymanagement.controller;

import com.example.librarymanagement.exception.BookNotFoundException;
import com.example.librarymanagement.model.Book;
import com.example.librarymanagement.service.BookService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/books")
public class BookController {

    private final BookService service;

    public BookController(BookService service) {
        this.service = service;
    }

    @GetMapping("/add")
    public String showAdd(Model model) {
        model.addAttribute("book", new Book());
        return "add-book";
    }

    @PostMapping("/add")
    public String addBook(@Valid @ModelAttribute("book") Book book,
                          BindingResult result) {

        if (result.hasErrors()) {
            return "add-book";
        }

        service.addBook(book);
        return "redirect:/books/view";
    }

    @GetMapping("/view")
    public String viewBooks(Model model) {
        model.addAttribute("books", service.getAllBooks());
        return "view-books";
    }

    @GetMapping("/{id}")
    public String bookDetails(@PathVariable Long id, Model model) throws BookNotFoundException {
        model.addAttribute("book", service.getBookById(id));
        return "book-details";
    }

    @GetMapping("/delete/{id}")
    public String deleteBook(@PathVariable Long id) throws BookNotFoundException {
        service.deleteBook(id);
        return "redirect:/books/view";
    }
}