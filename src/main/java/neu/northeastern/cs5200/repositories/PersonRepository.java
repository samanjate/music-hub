package neu.northeastern.cs5200.repositories;

import org.springframework.data.repository.CrudRepository;

import neu.northeastern.cs5200.models.Person;


public interface PersonRepository extends CrudRepository<Person, Integer> {

}
