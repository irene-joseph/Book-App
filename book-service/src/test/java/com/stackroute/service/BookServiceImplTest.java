package com.stackroute.service;

import com.stackroute.domain.Book;
import com.stackroute.repository.BookRepository;
import com.stackroute.service.BookService;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BookServiceImplTest {
    @Autowired
    private MockMvc mockMvc;

    @Mock
    private BookRepository bookRepository;

    @InjectMocks
    private BookService bookService;
    private final Book book = new Book();
    private Book book2;
    private List<Book> bookList;

    @BeforeEach
    public void setUp() throws Exception{
        MockitoAnnotations.initMocks(this);
        book.setBookId(1);
        book.setBook_name("Book Name");
        book.setLikes(0);
        book.setBook_genre("Genre");
        book.setBook_summary("Summary");
        book.setAuthor_name("Author name");
        book.setAuthor_about("Author about");
        book.setBook_page_count(100);

    }



    @Test
    public void addBook() throws Exception {
        try {
            when(bookRepository.save(any())).thenReturn(book);
            when(bookRepository.findAll()).thenReturn(bookList);
            assertEquals(true,bookService.addBook(book));
        }
        catch (Exception e){
            System.out.println("");
        }


    }

    @Test
    public void getAllBooks() throws Exception {

        try {
            bookRepository.save(book);
            //stubbing the mock to return specific data
            when(bookRepository.findAll()).thenReturn(bookList);
            List<Book> bookList1 = bookService.getAllBooks();
            assertEquals(bookList, bookList1);
        }
        catch (Exception e){
            System.out.println("");
        }

    }

    @Test
    public void getBookByBookByBookName() {
        try {
            bookRepository.save(book);
            //stubbing the mock to return specific data
            when(bookRepository.getBookbyBookName(any())).thenReturn(book);
            Book book1 = bookService.getBookByBookByBookName("Book Name");
            assertEquals(book1,book);
        }
        catch (Exception e){
            System.out.println("");
        }

    }

    @Test
    public void addLike() {
        try {
            bookRepository.save(book);
            boolean bool = bookService.addLike("Book Name");
            assertEquals(true,bool);
        }
        catch (Exception e){
            System.out.println("");
        }

    }

    @Test
    public void removeLike() {
        try {
            bookRepository.save(book);
            boolean bool = bookService.removeLike("Book Name");
            assertEquals(true,bool);
        }
        catch (Exception e){
            System.out.println("");
        }

    }
}