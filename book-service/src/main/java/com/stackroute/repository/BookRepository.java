package com.stackroute.repository;

import com.stackroute.domain.*;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Repository marks the specific class as a Data Access Object
 */
@Repository
public interface BookRepository extends CrudRepository<Book, Integer> {
    @Query("select book from Book book where book_genre= ?1 OR author_name =?2")
    List<Book> getRecommendationByBookgenreAndAuthorname(String book_genre, String author_name);

    @Query("select book from Book book where book_name= ?1")
    Book getBookbyBookName(String book_name);

}
