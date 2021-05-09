package com.stackroute.userjwtservice.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


/**
 * define an entity
 */
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Book {
    @Id
    /**
     * @Id annotation makes id variable as Primary key
     */
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int bookId;

    @Column(name = "book_name", nullable = false)
    private String book_name;

    @Column(name = "book_summary", nullable = false, length = 2000)
    private String book_summary;

    @Column(name = "book_genre", nullable = false)
    private String book_genre;

    @Column(name = "book_page_count", nullable = false)
    private int book_page_count;

    @Column(name = "author_name", nullable = false)
    private String author_name;

    @Column(name = "author_about", nullable = false, length = 2000)
    private String author_about;

    @Column(name="likes")
    private int likes;

}