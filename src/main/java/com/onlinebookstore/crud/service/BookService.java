package com.onlinebookstore.crud.service;

import com.onlinebookstore.crud.entity.Book;

import java.util.List;

public interface BookService {

    /**
     * This method is used to create book
     * @param book
     * @return book
     */
    public Book saveBook(Book book);

    /**
     * This method is used to create multiple book objects in database
     * @param books
     * @return
     */
    public List<Book> saveBooks(List<Book> books);

    /**
     * Fetch all books
     * @return list
     */
    public List<Book> getBooks();

    /**
     * Fetch book by id
     * @param id
     * @return book
     */
    public Book getBookById(int id);

    /**
     * delete book by id
     * @param id
     * @return string
     */

    public String deleteBook(int id);

    /**
     * update book by id
     * @param book
     * @return book
     */
    public Book updateBook(Book book);

    /**
     * This method is used to accumalte the total books price
     * if promocode is applicable and book type is FICTION
     * then apply 10% discount on fictional books and return total price
     * @param orders
     * @param promoCode
     * @return totalBooksPrice
     */
    public Double checkoutOrderPrice(List<Book> orders, String promoCode);
}
