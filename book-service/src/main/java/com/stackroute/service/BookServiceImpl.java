package com.stackroute.service;

import com.stackroute.controller.BookController;
import com.stackroute.domain.*;

import com.stackroute.repository.BookRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * @Service indicates annotated class is a service which hold business logic in the Service layer
 */
@Service
@AllArgsConstructor
public class BookServiceImpl implements BookService {
    @Autowired
    private BookRepository bookRepository;


    private static final Logger logger = (Logger) LoggerFactory.getLogger(BookController.class);

    /**
     * Implementation of addBook method
     */
    @Override
    public boolean addBook(Book newbook) {
        try {
            String newBook_name = newbook.getBook_name();
            String newBook_author = newbook.getAuthor_name();
            List<Book> list = (List<Book>) bookRepository.findAll();
            for (Book eachBook : list) {
                if(eachBook.getBook_name().equals(newBook_name)&&eachBook.getAuthor_name().equals(newBook_author)&&eachBook.getLikes()==newbook.getLikes()) {
                    return true;
                }
            }
            bookRepository.save(newbook);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Implementation of getAllBooks method
     */
    @Override
    public List<Book> getAllBooks() {
        return (List<Book>) bookRepository.findAll();
    }

    /**
     * Implementation of getBookByBookByBookName method
     */
    @Override
    public Book getBookByBookByBookName(String bookName) {
        return bookRepository.getBookbyBookName(bookName);
    }

    /**
     * Add a like to book
     */
    @Override
    public boolean addLike(String bookName) {
        Book book = bookRepository.getBookbyBookName(bookName);
        if(book!=null){
            int like = book.getLikes();
            like++;
            book.setLikes(like);
            bookRepository.save(book);
            return true;
        }
        return false;
    }

    /**
     * Remove a like to book
     */
    @Override
    public boolean removeLike(String bookName) {
        Book book = bookRepository.getBookbyBookName(bookName);
        if(book!=null){
            int like = book.getLikes();
            like--;
            book.setLikes(like);
            bookRepository.save(book);
            return true;
        }
        return false;
    }


    /**
     * Implementation of getRecommendationByBook method
     */
    @Override
    public List<Book> getRecommendationByBook(Book book) {
        if (bookRepository.existsById(book.getBookId())){
            List<Book> fetched_list = bookRepository.getRecommendationByBookgenreAndAuthorname(book.getBook_genre(),book.getAuthor_name());
            List<Book> recommendation_list = new ArrayList<>();
            for (Book eachBook : fetched_list) {
                if(!eachBook.getBook_name().equals(book.getBook_name())) {
                    recommendation_list.add(eachBook);
                }
            }
        return recommendation_list;
    }
    return null;}


}

