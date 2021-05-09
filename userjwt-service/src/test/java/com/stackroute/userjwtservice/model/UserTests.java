package com.stackroute.userjwtservice.model;

import org.junit.Before;
import org.junit.Test;
import org.meanbean.test.BeanTester;

public class UserTests {

private User user;
	
	@Before
	public void setUp() {
		user = new User();
		user.setUsername("joseph");
		user.setPassword("12345678");
		user.setEmail("joseph@gmail.com");
		user.setFirstname("P J");
		user.setLastname("Joseph");
	}

        @Test
    	public void test() {
    		new BeanTester().testBean(User.class);
    	}
}
