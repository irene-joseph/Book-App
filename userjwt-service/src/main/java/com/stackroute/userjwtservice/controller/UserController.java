package com.stackroute.userjwtservice.controller;

import java.util.*;

import javax.servlet.ServletException;

import com.stackroute.userjwtservice.model.Book;
import com.stackroute.userjwtservice.service.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.impl.client.HttpClients;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.*;

import com.stackroute.userjwtservice.model.User;
import com.stackroute.userjwtservice.service.UserService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.web.client.RestTemplate;

@RestController
@CrossOrigin(value = "*")
@RequestMapping("/api")
@Slf4j
public class UserController {

	// Defining expiry time of the JWTtoken in milliseconds
	long expireTime = 12000000;

	private static final Logger logger = (Logger) LoggerFactory.getLogger(UserController.class);


	ClientHttpRequestFactory requestFactory = new
			HttpComponentsClientHttpRequestFactory(HttpClients.createDefault());

	@Autowired
	RestTemplate restTemplate = new RestTemplate(requestFactory);

	@Autowired
	private UserServiceImpl userService;

	/**
	 * End point for getting a user registered,
	 * If registered successfully returning status as Created (201)
	 * Otherwise returning status as Conflict
	 */
	@PostMapping("/register")
	public ResponseEntity<String> registerUser(@RequestBody User user) {
		if(!userService.checkAllFields(user)){
			return new ResponseEntity<String>("All the fields are mandatory", HttpStatus.CONFLICT);
		}
		boolean bool = userService.addUser(user);
		if(bool) {
			return new ResponseEntity<String>("user registered successfully", HttpStatus.CREATED);
		}
		else return new ResponseEntity<String>("user already exists, try logging in", HttpStatus.CONFLICT);

	}


	/**
	 * End point for validating an already registered user,
	 * If validated successfully returning status as OK (200)
	 * And also Return the Token
	 * If not validated then the token will contain null
	 */
	@PostMapping("/login")
	public ResponseEntity<?> loginUser(@RequestBody User user) {

		String jwtToken = "";
		Map<String,String> map = new HashMap<>();

		try {

			// Calling the getToken method as written below
			jwtToken = getToken(user.getUsername(),user.getPassword());
			map.clear();
			map.put("message", "User logged in successfully");
			map.put("Token",jwtToken);

		}catch(Exception e) {
			// In case of exception returning the error message and the null in place of token
			map.clear();
			if(e.getMessage().equals("No value present")){
				map.put("message","User not found");
			}
			else{
				map.put("message","Invalid Credentials");
			}
			map.put("Token",null);
		}
		return new ResponseEntity<>(map,HttpStatus.OK);
	}

	/**
	 * End point for getting all available books
	 */
	@GetMapping(value = "/books")
	public Book[] getBookList() {
		ResponseEntity<Book[]> response =
				restTemplate.getForEntity(
						"http://localhost:8004/api/books",
						Book[].class);
		Book[] books = response.getBody();
		return books;
	}

	/**
	 * End point for getting a book using book name
	 */
	@GetMapping(value = "/book/{book_name}")
	public Book getBookbyBookName(@PathVariable String book_name) {
		Map < String, String > params = new HashMap < String, String > ();
		params.put("book_name", book_name);

		Book book = restTemplate.getForObject("http://localhost:8004/api/book/{book_name}", Book.class, params);
		return book;
	}


	/**
	 * End point for deleting an already registered user,
	 * If deleted successfully returning status as OK (200)
	 * Otherwise returning status as NOT_FOUND (404)
	 */
	@DeleteMapping("/user")
	public ResponseEntity<String> deleteUser(@RequestParam String username, @RequestParam String password){
		if(userService.deleteUser(username, password)) {
			return new ResponseEntity<String>("deleted successfully", HttpStatus.OK);
		}
		else return new ResponseEntity<String>("cannot delete, user not found", HttpStatus.NOT_FOUND);

	}

	/**
	 * End point for updating password of an already registered user,
	 * If updated successfully returning status as OK (200)
	 * Otherwise returning status as NOT_FOUND (404)
	 */
	@PutMapping("/user")
	public ResponseEntity<String> update(@RequestBody Map<String, String> json){
		if(userService.updateUser(json.get("username"), json.get("oldpassword"), json.get("newpassword"))) {
			return new ResponseEntity<String>("password updated successfully", HttpStatus.OK);
		}
		else return new ResponseEntity<String>("error in updating password! check your username and old password.", HttpStatus.NOT_FOUND);
	}


	/**
	 * End point for getting favourites of an already registered user,
	 * If updated successfully returning status as OK (200)
	 * Otherwise returning status as NOT_FOUND (404)
	 */
	@GetMapping("/user")
	public ResponseEntity<?> getAllFavourites(@RequestParam String username){
		List<Book> fetchedList = userService.getAllFavourites(username);
		if(!fetchedList.isEmpty()) {
			return new ResponseEntity<List<Book>>(fetchedList, HttpStatus.OK);
		}
		else return new ResponseEntity<String>("error fetching favourites!", HttpStatus.NOT_FOUND);
	}


	/**
	 * End point for adding a book as favourite,
	 * If added successfully returning status as OK (200)
	 * Otherwise returning status as NOT_FOUND (404)
	 */
	@PostMapping("/user/{book_name}")
	public ResponseEntity<String> addFavourite(@PathVariable String book_name,@RequestBody Map<String, String> json ) {
		Map < String, String > params = new HashMap < String, String > ();
		params.put("book_name", book_name);
		Book book = getBookbyBookName(book_name);
		String response = restTemplate.getForObject("http://localhost:8004/api/like/{book_name}", String.class, params);
		if(userService.addFavourite(json.get("username"), book)){
			return new ResponseEntity<String>("added to your favourite list", HttpStatus.OK);
		}
		else return new ResponseEntity<String>("error in adding the book! Book already exists in your favourite list if not, please check the name of the book.", HttpStatus.NOT_FOUND);
	}

	/**
	 * End point for removing a book as favourite,
	 * If removed successfully returning status as OK (200)
	 * Otherwise returning status as NOT_FOUND (404)
	 */
	@PostMapping("/user/remove/{book_name}")
	public ResponseEntity<String> removeFavourite(@RequestBody Map<String, String> json, @PathVariable String book_name){
		Map < String, String > params = new HashMap < String, String > ();
		params.put("book_name", book_name);
		Book book = getBookbyBookName(book_name);
		String response = restTemplate.getForObject("http://localhost:8004/api/remove/{book_name}", String.class, params);
		if(userService.removeFavourite(json.get("username"), book)){
			return new ResponseEntity<String>("removed from your favourite list", HttpStatus.OK);
		}
		else return new ResponseEntity<String>("Error in removing the book! please check the name of the book.", HttpStatus.NOT_FOUND);
	}


	/**
	 *method for getting token string
	 */
	public String getToken(String username,String password) throws Exception {

		// If either of username or password field is empty it will throw exception
		if(username == null || password == null) {
			throw new ServletException("Please fill the Username and Password");
		}

		// Calling the validate(String username, String password) of return type boolean
		boolean status = userService.validate(username,password);

		// Throwing exception if the user is not a valid user
		if(!status) {
			throw new ServletException("Invalid Credentials");
		}

		// Generating token only when the user is validated
		String jwtToken = Jwts.builder().setSubject(username).setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis()+ expireTime))
				.signWith(SignatureAlgorithm.HS256, "secret").compact();
		return jwtToken;
	}

}
