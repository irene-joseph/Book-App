package com.stackroute.userjwtservice.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.stackroute.userjwtservice.model.Book;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.junit.jupiter.api.Assertions;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stackroute.userjwtservice.model.User;
import com.stackroute.userjwtservice.service.UserService;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@WebMvcTest
public class UserAuthControllerTests {

	@Autowired
	private MockMvc mockMvc;
	@MockBean
	private User user;
	@MockBean
	UserService userService;
	@InjectMocks
	UserController userController;
	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
		user = new User();
		user = new User();
		user.setUsername("joseph");
		user.setPassword("12345678");
		user.setEmail("joseph@gmail.com");
		user.setFirstname("P J");
		user.setLastname("Joseph");
	}


	@Test
	public void registerUserSuccess() throws Exception  {
		when(userService.addUser(any())).thenReturn(true);
		when(userService.checkAllFields(any())).thenReturn(true);
		mockMvc.perform(post("/api/register")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
				.andExpect(status().isCreated()).andDo(MockMvcResultHandlers.print());
	}


	@Test
	public void registerUserFailure() throws Exception {
		when(userService.addUser(any())).thenReturn(false);
		mockMvc.perform(post("/api/register")
				.contentType(MediaType.APPLICATION_JSON).content(asJsonString(user)))
				.andExpect(status().isConflict()).andDo(MockMvcResultHandlers.print());
	}


	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
}
