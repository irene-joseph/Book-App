package com.stackroute.domain;

import com.stackroute.domain.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.meanbean.test.BeanTester;

import static org.junit.jupiter.api.Assertions.*;

public class BookTest {

    private Book book;

    @Before
    public void setUp() {
        book = new Book();
        book.setBookId(1);
        book.setBook_name("Book Name");
        book.setLikes(0);
        book.setBook_genre("Genre");
        book.setBook_summary("Summary");
        book.setAuthor_name("Author name");
        book.setAuthor_about("Author about");
        book.setBook_page_count(100);
    }
    @AfterEach
    void tearDown() {
    }

    @Test
    public void test() {
        new BeanTester().testBean(Book.class);
    }
}