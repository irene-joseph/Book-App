package com.stackroute.service;

import com.stackroute.domain.*;
import java.util.List;

public interface BookService {

    /**
     * AbstractMethod to add a book
     */
    boolean addBook(Book book);

    /**
     * AbstractMethod to get all books
     */
    List<Book> getAllBooks();

    /**
     * AbstractMethod to get book by book name
     */
    Book getBookByBookByBookName(String bookName);

    /**
     * AbstractMethod to add a like
     */
    boolean addLike(String bookName);

    /**
     * AbstractMethod to remove a like
     */
    boolean removeLike(String bookName);


    /**
     * AbstractMethod to get recommended books by book name
     */
    List<Book> getRecommendationByBook(Book book);
}
