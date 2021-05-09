package com.stackroute.userjwtservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.stackroute.userjwtservice.model.User;


@Repository
public interface UserRepository extends CrudRepository<User, String> {

	
}
