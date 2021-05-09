package com.stackroute.userjwtservice.service;


import org.mindrot.jbcrypt.BCrypt;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.stackroute.userjwtservice.model.User;
import com.stackroute.userjwtservice.repository.UserRepository;
import org.springframework.web.client.RestTemplate;

import com.stackroute.userjwtservice.model.Book;

import java.util.ArrayList;
import java.util.List;


@Service
public class UserServiceImpl implements UserService{

	@Autowired
	private UserRepository userRepository;

	private static final Logger logger = (Logger) LoggerFactory.getLogger(UserService.class);


	/**Method to Delete the user info
	 * Checking if the user exists and if it exists then deleting it and returning true
	 * Otherwise returning false
	*/
	@Override
	public boolean deleteUser(String username, String password) {
		if(userRepository.findById(username).isPresent()) {
			if(validate(username,password)){
			userRepository.deleteById(username);
			return true;}
			return false;
		}
		else return false;
		
	}
	
	/**Method to Add the user info of new user
	 * Using save() method of CRUD Repository interface
	 * Returning True if the user info is saved
	 * Otherwise returning false in case any exception occurs or if the user already exists
	*/
	@Override
	public boolean addUser(User user) {
		try {
			if(!userRepository.existsById(user.getUsername())) {
				String password=hashPassword(user.getPassword());
				user.setPassword(password);
				userRepository.save(user);
				return true;
			}
			else return false;
		}catch(Exception e) {
			return false;
		}
	}

	/**
	 *Method to encrypt a password
	 */
	public String hashPassword(String plainTextPassword){

		return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
	}

	/**
	 * Method to check whether all fields are filled
	 */
	@Override
	public boolean checkAllFields(User user) {
		if(user.getUsername().replaceAll("\\s+","").equals("")
				||user.getPassword().replaceAll("\\s+","").equals("")
				||user.getFirstname().replaceAll("\\s+","").equals("")
				||user.getEmail().replaceAll("\\s+","").equals("")
				||user.getFirstname()==null||user.getEmail()==null||user.getLastname()==null
				||user.getUsername()==null||user.getPassword()==null)
		{return false;}
		return true;
	}

	/**
	 *Method to add a favourite book for a user
	 */
	@Override
	public boolean addFavourite(String username, Book book){
		if(userRepository.existsById(username)){
		User user = userRepository.findById(username).get();
		List<Book> FavouriteList = user.getFavouriteBooks();
		if(FavouriteList.indexOf(book)>=0){return false;}
		FavouriteList.add(book);
		user.setFavouriteBooks(FavouriteList);
		userRepository.save(user);
		return true;
		}
		return false;
	}

	/**
	 *Method to remove a favourite book for a user
	 */
	@Override
	public boolean removeFavourite(String username, Book book) {
		if(userRepository.existsById(username)){
			User user = userRepository.findById(username).get();
			List<Book> FavouriteList = user.getFavouriteBooks();
			List<Book> updatedFavouriteList = new ArrayList<>();
			for (Book eachBook:FavouriteList
				 ) {
				if(eachBook!=book){
					updatedFavouriteList.add(eachBook);
				}
			}
			FavouriteList.remove(book);
			user.setFavouriteBooks(updatedFavouriteList);
			userRepository.save(user);
			return true;
		}
		return false;
	}

	/**
	 *Method to get all favourites of a user
	 */
	@Override
	public List<Book> getAllFavourites(String username) {
		User user = userRepository.findById(username).get();
		return user.getFavouriteBooks();
	}



	/**Method to Check if the username and password matches
	 * Returning True if the credentials correct
	 * Otherwise return false
	*/
	@Override
	public boolean validate(String username, String password) {
		User user=userRepository.findById(username).get();
		String passwordhash=user.getPassword();
		if (BCrypt.checkpw(password, passwordhash))
		{
			return true;
		}
		else {
			return false;
		}
	}

	
	/** Method to update the password of user
	 * Checking if the old password provided by him is correct
	 * Updating and Returning True if the credentials correct
	 * Otherwise return false
	*/
	@Override
	public boolean updateUser(String username, String oldpass, String newpass) {
		if(userRepository.existsById(username)) {
			if(validate(username, oldpass)) {
				try {
					User u = userRepository.findById(username).get();
					u.setUsername(username);
					String newpassword=hashPassword(newpass);
					u.setPassword(newpassword);
					userRepository.save(u);
					return true;
				}catch (Exception e) {
					return false;
				}
			}
			else return false;
		}
		else return false;
	}

}
