package com.stackroute.userjwtservice.service;

import com.stackroute.userjwtservice.model.User;
import com.stackroute.userjwtservice.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class UserServiceImplTest {

	@Mock
    UserRepository userRepository;
	
	User user;
	
	@InjectMocks
   UserServiceImpl userService;
	
	@Before
	public void setUp() {
       MockitoAnnotations.initMocks(this);
       user = new User();
       user.setUsername("shivaagn");
       user.setPassword("password");
	}
	@Test
   public void addUserSuccess(){
       when(userRepository.save((User) any())).thenReturn(user);
       User userSaved = userRepository.save(user);
       assertEquals(user, userSaved);
   }
}
