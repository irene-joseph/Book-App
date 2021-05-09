package com.stackroute.userjwtservice.service;

import com.stackroute.userjwtservice.model.Book;
import com.stackroute.userjwtservice.model.User;

import java.util.List;

//This is an interface which is implemented by userServiceImpl class

public interface UserService {
	
	public boolean deleteUser(String u, String p);
	public boolean addUser(User u);
	public boolean validate(String username,String password);
	public boolean updateUser(String username, String oldpass, String newpass);
	public String hashPassword(String plainTextPassword);
	public boolean checkAllFields(User u);
	public List<Book> getAllFavourites(String username);
	public boolean addFavourite(String username, Book book);
	public boolean removeFavourite(String username, Book book);

}
