# book-app
*Book App* is a Spring Boot Application that allows users to:
  1. search all the available books. search a book by book name. 
  2. get book recommendations based on book genre and author. 
  3. create a user profile(registration) 
  4. access the user profile(login)  
  5. add/delete books as favourites. 
  6. update the password. 
  7. delete his/her user profile.

 *Tools and Technologies used*:
 
 
    1. Middleware:  Spring Boot
    2. IDE: IntelliJ
    3. Programming Language: Java
    4. Frond end:  Postman
    5. VCS:  Gitlab
    6. Database :  MySQL
    7. Testing:  Junit, Mockito
    8. Documentation: Swagger

*Book-service API*
  1. Book properties

    1. int book_id
    2. String book_name
    3. String book_summary
    4. String book_genre
    5. int book_page_count
    6. String author_name
    7. String author_about
    8. int likes
    
  2. Services

    1. Get all available books.
    2. Search book by book_name.
    3. Get recommended books based on book_genre and author_name.
    4. Add a like.
    5. Remove a like.

*User-jwt-service API*
  1. User properties

    1. String username
    2. String password(stored in encrypted format)
    3. String Email
    4. String Firstname
    5. String Lastname
    6. List<Book> Favourite_books(accessed using restTemplate)
    
  2. Services

    1. Create user profile(registration)
    2. Login(authentication using JWT and token is generated)
    3. Authenticated user can update his/her password.
    4. Authenticated user can delete his/her profile.
    5. Authenticated user can add books as favourites.
    6. Authenticated user can remove books from favourites list.
    7. Authenticated user can view his/her favourite list.
    8. Users can access all the book services of book-service API via user-jwt-service API.

Book-service API and user-jwt-service API can be registered to a eureka server via *Eureka-server* module and eureka server can be accessed at port 8761.

A *API-gateway* service module is included and both(Book-service API and user-jwt-service API) services can be accessed via this API gateway port.

Documentation is done using *Swagger*
