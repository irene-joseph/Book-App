package com.stackroute.controller;

import com.stackroute.domain.*;
import com.stackroute.service.BookService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;

/**
 * RestController annotation is used to create Restful web services using Spring MVC
 */
@RestController
/**
 * RequestMapping annotation maps HTTP requests to handler methods
 */
@Slf4j
@AllArgsConstructor
@RequestMapping(value = "/api")
public class BookController {

    @Autowired
    private BookService bookService;

    private static final Logger logger = (Logger) LoggerFactory.getLogger(BookController.class);

    /**
     * End point for adding a  book in the DB
     */
    @PostMapping("/book")
    public ResponseEntity<?> addFavoriteBook(@RequestBody Book newbook) {
        if(bookService.addBook(newbook)) {
            return new ResponseEntity<String>("Successfully added the book", HttpStatus.CREATED);
        }
        else return new ResponseEntity<String>("Error adding the book!", HttpStatus.CONFLICT);
    }

    /**
     * retrieve all books
     */
    @GetMapping("/books")
    public ResponseEntity<List<Book>> getAllBooks() {
        return new ResponseEntity<List<Book>>((List<Book>) bookService.getAllBooks(), HttpStatus.OK);

    }

    /**
     * retrieve book by id
     */
    @GetMapping("/book/{book_name}")
    public ResponseEntity<?> getBookById(@PathVariable String book_name) {
        try{
        return new ResponseEntity<Book>(bookService.getBookByBookByBookName(book_name), HttpStatus.FOUND);}
        catch (Exception e){
            return new ResponseEntity<>("Book of given book name is not found",HttpStatus.CONFLICT);
        }
    }

    /**
     * recommended books by book name(based on book genre and author name)
     */
    @GetMapping("/recommendation")
    public ResponseEntity<?> getAllRecommendedBooks(@RequestBody Book book) {
        try {
            List<Book> recommendedBookList = bookService.getRecommendationByBook(book);
            if(recommendedBookList.isEmpty()){
                return new ResponseEntity<String>("No recommended books available",HttpStatus.CONFLICT);
            }
            return new ResponseEntity<List<Book>>(recommendedBookList,HttpStatus.OK);
        }catch (Exception e) {
            return new ResponseEntity<String>("Error fetching recommended books",HttpStatus.CONFLICT);
        }
    }


    @GetMapping("/like/{book_name}")
    public ResponseEntity<?> addLike(@PathVariable String book_name){
        try{
            bookService.addLike(book_name);
            return new ResponseEntity<>("Added a like", HttpStatus.FOUND);}
        catch (Exception e){
            return new ResponseEntity<>("Book of given book name is not found",HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/remove/{book_name}")
    public ResponseEntity<?> removeLike(@PathVariable String book_name){
        try{
            bookService.removeLike(book_name);
            return new ResponseEntity<>("Removed a like", HttpStatus.FOUND);}
        catch (Exception e){
            return new ResponseEntity<>("Book of given book name is not found",HttpStatus.CONFLICT);
        }
    }
}