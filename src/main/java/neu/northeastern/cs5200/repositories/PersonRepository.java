package neu.northeastern.cs5200.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import neu.northeastern.cs5200.models.Person;


public interface PersonRepository extends CrudRepository<Person, Integer> {
	
	@Query("SELECT p FROM Person p WHERE p.email = :email and p.password = :password")
	public List<Person> findUserByCredentials(@Param("email") String email, @Param("password") String password);

}
