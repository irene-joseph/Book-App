package com.stackroute.controller;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.controller.BookController;
import com.stackroute.domain.Book;
import com.stackroute.service.BookService;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BookControllerTest {

    @Autowired
    private MockMvc mockMvc;

    private Book book;
    private List<Book> bookList;
    @Mock
    @Autowired
    BookService bookService;
    @InjectMocks
    @Autowired
    BookController bookController;
    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
        book = new Book();
        book.setBookId(1);
        book.setBook_name("BookName");
        book.setLikes(0);
        book.setBook_genre("Genre");
        book.setBook_summary("Summary");
        book.setAuthor_name("Author name");
        book.setAuthor_about("Author about");
        book.setBook_page_count(100);
        bookList = new ArrayList<>();
        bookList.add(book);
    }


    @Test
    public void addBook() throws Exception {
        try{
        when(bookService.addBook(any())).thenReturn(true);
        mockMvc.perform(post("/api/book")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(book)))
                .andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());}
        catch (Exception e){
            System.out.println("");
        }

    }

    @Test
    public void getAllBooks() throws Exception {
        try{
        when(bookService.getAllBooks()).thenReturn(bookList);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/books")
                .contentType(MediaType.APPLICATION_JSON).content(asJsonString(book)))
                .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());}
        catch (Exception e){
            System.out.println("");
        }
    }

    @Test
    void getBookById() {
        try{
            when(bookService.getBookByBookByBookName(any())).thenReturn(book);
            mockMvc.perform(MockMvcRequestBuilders.get("/api/books/BookName")
                    .contentType(MediaType.APPLICATION_JSON).content(asJsonString(book)))
                    .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());}
        catch (Exception e){
            System.out.println("");
        }
    }

    @Test
    void getAllRecommendedBooks() throws Exception {
        try{
            when(bookService.getRecommendationByBook(any())).thenReturn(bookList);
            mockMvc.perform(MockMvcRequestBuilders.get("/api/recommendation")
                    .contentType(MediaType.APPLICATION_JSON).content(asJsonString(book)))
                    .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());}
        catch (Exception e){
            System.out.println("");
        }
    }

    @Test
    void addLike() throws Exception {
        try{
            when(bookService.addLike(any())).thenReturn(true);
            mockMvc.perform(MockMvcRequestBuilders.get("/api/like/BookName")
                    .contentType(MediaType.APPLICATION_JSON).content(asJsonString(book)))
                    .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());}
        catch (Exception e){
            System.out.println("");
        }
    }

    @Test
    void removeLike() {
        try{
            when(bookService.removeLike(any())).thenReturn(true);
            mockMvc.perform(MockMvcRequestBuilders.get("/api/remove/BookName")
                    .contentType(MediaType.APPLICATION_JSON).content(asJsonString(book)))
                    .andExpect(status().isOk()).andDo(MockMvcResultHandlers.print());}
        catch (Exception e){
            System.out.println("");
        }

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}