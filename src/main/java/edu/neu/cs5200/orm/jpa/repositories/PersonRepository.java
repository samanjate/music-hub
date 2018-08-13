package edu.neu.cs5200.orm.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Person;


public interface PersonRepository extends CrudRepository<Person, Integer> {
	
	@Query("SELECT p FROM Person p WHERE p.username = :username and p.password = :password")
	public List<Person> findUserByCredentials(@Param("username") String username, @Param("password") String password);

}
