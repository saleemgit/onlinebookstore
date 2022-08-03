package com.onlinebookstore.crud.controller;


import com.onlinebookstore.crud.entity.Book;
import com.onlinebookstore.crud.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bookStore")
public class BookController {

    Logger logger = LoggerFactory.getLogger(BookController.class);

    @Autowired
    private BookService service;

    @PostMapping("/addBook")
    public Book addBook(@RequestBody Book book) {
        logger.info("Adding new book : {}", book);
        return service.saveBook(book);
    }

    @PostMapping("/addBooks")
    public List<Book> addBooks(@RequestBody List<Book> books) {
        logger.info("Adding multiple books : {}", books.size());
        return service.saveBooks(books);
    }

    @GetMapping("/books")
    public List<Book> findAllBooks() {
        logger.info("Fetching all books");
        return service.getBooks();
    }

    @GetMapping("/book/{id}")
    public Book findBookById(@PathVariable int id) {
        logger.info("Fetching Book by id : {}", id);
        return service.getBookById(id);
    }

    @PutMapping("/updateBook")
    public Book updateBook(@RequestBody Book book) {
        logger.info("Updating Book : {}", book);
        return service.updateBook(book);
    }

    @DeleteMapping("/delete/{id}")
    public String deleteBook(@PathVariable int id) {
        logger.info("Deleting Book by id : {}", id);
        return service.deleteBook(id);
    }

    @GetMapping("/checkoutOrderPrice")
    public Double checkoutOrder(@RequestBody List<Book> orders,
                                @RequestParam(value = "promoCode", required = false) String promoCode) {
        return service.checkoutOrderPrice(orders, promoCode);
    }
}
