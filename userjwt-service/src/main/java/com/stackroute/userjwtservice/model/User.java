package com.stackroute.userjwtservice.model;

import lombok.*;
import org.checkerframework.common.aliasing.qual.Unique;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import java.util.List;


@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
	@Id
	@Column(name = "username",unique = true, nullable = false)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "firstname", nullable = false)
	private String firstname;

	@Column(name = "lastname", nullable = false)
	private String lastname;

	@Column(name = "email",unique = true, nullable = false)
	@Email
	@Unique
	private String email;

	@ElementCollection
	@Column(name = "favouriteBooks")
	private List<Book> favouriteBooks;
}


