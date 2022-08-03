package com.onlinebookstore.crud.service;

import com.onlinebookstore.crud.entity.Book;
import com.onlinebookstore.crud.exception.InvalidDataException;
import com.onlinebookstore.crud.repository.BookRepository;
import com.onlinebookstore.crud.service.impl.BookServiceImpl;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RunWith(MockitoJUnitRunner.class)
public class BookServiceTest {

    @InjectMocks
    private BookServiceImpl bookService;

    @Mock
    private BookRepository bookRepository;

    @Test
    public void testSaveBook() {
        Book book = new Book();
        book.setIsbn(1);
        book.setName("test");
        book.setDescription("description");
        book.setPrice(1000d);
        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(book);
        Book response = bookService.saveBook(book);
        Assert.assertNotNull(response);
        Assert.assertEquals("test", response.getName());
    }

    @Test
    public void testSaveBooks() {
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setIsbn(1);
        book.setName("test");
        book.setDescription("description");
        book.setPrice(1000d);
        books.add(book);
        Mockito.when(bookRepository.saveAll(Mockito.anyList())).thenReturn(books);
        List<Book> response = bookService.saveBooks(books);
        Assert.assertEquals(1, response.size());
        Assert.assertEquals(1, response.get(0).getIsbn());
    }

    @Test
    public void testFetchBooks() {
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setIsbn(1);
        book.setName("comic");
        book.setDescription("comic test book description");
        book.setPrice(1000d);
        books.add(book);
        Book book2 = new Book();
        book2.setIsbn(2);
        book2.setName("fictional");
        book2.setDescription("fictional test book description");
        book2.setPrice(2000d);
        books.add(book2);
        Mockito.when(bookRepository.findAll()).thenReturn(books);
        List<Book> response = bookService.getBooks();
        Assert.assertEquals(2, response.size());
        Assert.assertEquals("comic", response.get(0).getName());
    }

    @Test
    public void testFetchBookById() throws Exception {
        Book book = new Book();
        book.setIsbn(1);
        book.setName("Novel");
        book.setDescription("novel test book description");
        book.setPrice(400d);
        Mockito.when(bookRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(book));
        Book response = bookService.getBookById(1);
        Assert.assertNotNull(response);
        Assert.assertEquals("Novel", response.getName());
    }

    @Test(expected = InvalidDataException.class)
    public void testFetchBookByIdError() {
        Book book = new Book();
        book.setIsbn(1);
        book.setName("Novel");
        book.setDescription("novel test book description");
        book.setPrice(400d);
        Mockito.when(bookRepository.findById(Mockito.eq(2))).thenReturn(Optional.empty());
        bookService.getBookById(2);
    }

    @Test
    public void testDeleteBookById() {
        Book book = new Book();
        book.setIsbn(1);
        book.setName("Tragedy");
        book.setDescription("Tragedy test book description");
        book.setPrice(700d);
        Mockito.when(bookRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(book));
        String response = bookService.deleteBook(1);
        Assert.assertNotNull(response);
    }

    @Test(expected = InvalidDataException.class)
    public void testDeleteBookByIdError() {
        Book book = new Book();
        book.setIsbn(1);
        book.setName("Tragedy");
        book.setDescription("Tragedy test book description");
        book.setPrice(700d);
        bookService.deleteBook(1);
    }

    @Test(expected = InvalidDataException.class)
    public void testUpdateBookNotFound() {
        Book book = new Book();
        book.setIsbn(1);
        book.setName("Tragedy");
        book.setDescription("Tragedy test book description");
        book.setPrice(900d);
        Mockito.when(bookRepository.findById(Mockito.anyInt())).thenReturn(Optional.empty());
        bookService.updateBook(book);
    }

    @Test
    public void testUpdateBook() {
        Book book = new Book();
        book.setIsbn(1);
        book.setName("Tragedy");
        book.setDescription("Tragedy test book description");
        book.setPrice(900d);
        Mockito.when(bookRepository.findById(Mockito.anyInt())).thenReturn(Optional.of(book));
        Mockito.when(bookRepository.save(Mockito.any())).thenReturn(book);
        Book response = bookService.updateBook(book);
        Assert.assertNotNull(response);
        Assert.assertEquals("900.0", response.getPrice().toString());
    }

    @Test
    public void testCheckOutBookPrice() {
        List<Book> books = new ArrayList<>();
        Book book = new Book();
        book.setIsbn(1);
        book.setName("The Walkyre");
        book.setType("COMIC");
        book.setDescription("comic test book description");
        book.setPrice(1000d);
        books.add(book);
        Book book2 = new Book();
        book2.setIsbn(2);
        book2.setName("The Truth");
        book2.setType("FICTION");
        book2.setDescription("fictional test book description");
        book2.setPrice(2000d);
        books.add(book2);
        Double totalBooksPrice = bookService.checkoutOrderPrice(books, "FICTION");
        Assert.assertNotNull(totalBooksPrice);
        Assert.assertTrue(totalBooksPrice.compareTo(2800d) == 0);
    }
}
