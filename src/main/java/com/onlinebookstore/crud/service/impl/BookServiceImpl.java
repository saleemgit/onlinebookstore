package com.onlinebookstore.crud.service.impl;

import com.onlinebookstore.crud.BookType;
import com.onlinebookstore.crud.entity.Book;
import com.onlinebookstore.crud.exception.InvalidDataException;
import com.onlinebookstore.crud.exception.model.ErrorCode;
import com.onlinebookstore.crud.repository.BookRepository;
import com.onlinebookstore.crud.service.BookService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    private static final String promoDiscountRate = "10%";
    private static final Double discountRate = 0.10d;

    @Autowired
    private BookRepository repository;

    @Override
    public Book saveBook(Book book) {
        return repository.save(book);
    }

    @Override
    public List<Book> saveBooks(List<Book> books) {
        return repository.saveAll(books);
    }

    @Override
    public List<Book> getBooks() {
        return repository.findAll();
    }

    @Override
    public Book getBookById(int id) {
        return repository.findById(id).orElseThrow(()->{
            return new InvalidDataException(ErrorCode.INVALID_DATA.getErrorCode(), ErrorCode.INVALID_DATA.getMessage());
        });
    }

    @Override
    public String deleteBook(int id) {
        repository.findById(id).orElseThrow(() -> {
            return new InvalidDataException(ErrorCode.DATA_NOT_FOUND.getErrorCode(), ErrorCode.DATA_NOT_FOUND.getMessage());
        });
        repository.deleteById(id);
        return "Book deleted "+id;
    }

    @Override
    public Book updateBook(Book book) {
        Book existingBook = repository.findById(book.getIsbn()).orElseThrow(() -> {
            return new InvalidDataException(ErrorCode.INVALID_DATA.getErrorCode(), ErrorCode.INVALID_DATA.getMessage());
        });
        existingBook.setName(book.getName());
        existingBook.setType(book.getType());
        existingBook.setPrice(book.getPrice());
        existingBook.setDescription(book.getDescription());
        return repository.save(existingBook);
    }

    @Override
    public Double checkoutOrderPrice(List<Book> orders, String promoCode) {
        Double fictionalBooksPrice = orders.stream().filter(book -> BookType.FICTION.name().equalsIgnoreCase(book.getType()) && Double.compare(book.getPrice(), 0d) > 0)
                .mapToDouble(Book::getPrice).sum();
        logger.info("Fictional books price : {}", fictionalBooksPrice);

        /**
         * As per documentation, no eligibility criteria given for promoCode
         * so just checking the promoCode is not null and book type is FICTION
         * and then apply discount as 10%
         * else not
         */
        if (Optional.ofNullable(promoCode).isPresent()) {
            logger.info("PromoCode is found, so discount on Fictional book is : {}", promoDiscountRate);
            Double discountPrice = fictionalBooksPrice * discountRate;
            fictionalBooksPrice = fictionalBooksPrice - discountPrice;
            logger.info("Fictional books price with discount : {}", fictionalBooksPrice);
        }
        logger.info("Fictional books after discount price : {}", fictionalBooksPrice);

        Double otherBooksPrice = orders.stream().filter(book -> !BookType.FICTION.name().equalsIgnoreCase(book.getType()) && Double.compare(book.getPrice(), 0d) > 0)
                .mapToDouble(Book::getPrice).sum();

        logger.info("Other Books price : {}", otherBooksPrice);

        return Double.sum(fictionalBooksPrice, otherBooksPrice);
    }
}
