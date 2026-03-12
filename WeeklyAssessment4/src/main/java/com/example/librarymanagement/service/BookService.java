package com.example.librarymanagement.service;

import com.example.librarymanagement.exception.BookNotFoundException;
import com.example.librarymanagement.model.Book;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BookService {

    private List<Book> books = new ArrayList<>();
    private Long idCounter = 1L;

    public void addBook(Book book){
        book.setId(idCounter++);
        books.add(book);
    }

    public List<Book> getAllBooks() {
        return books;
    }

    public Book getBookById(Long id) throws BookNotFoundException {
        return books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElseThrow(() ->
                        new BookNotFoundException("Book with ID " + id + " not found"));
    }

    public void deleteBook(Long id) throws BookNotFoundException {
        Book book = getBookById(id);
        books.remove(book);
    }


}
