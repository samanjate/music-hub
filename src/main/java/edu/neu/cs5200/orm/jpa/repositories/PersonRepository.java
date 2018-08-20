package edu.neu.cs5200.orm.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.entities.Playlist;

public interface PersonRepository extends CrudRepository<Person, Integer> {

	@Query("SELECT p FROM Person p WHERE p.username = :username and p.password = :password")
	public List<Person> findUserByCredentials(@Param("username") String username, @Param("password") String password);

	@Query("SELECT p FROM Person p WHERE p.username like :username")
	public List<Person> findUserByUsername(@Param("username") String username);

	@Query("SELECT p.following FROM Person p WHERE p.id = :id")
	public List<Person> findUserFollowing(@Param("id") int id);

	@Query("SELECT p.playlists FROM Person p WHERE p.id like :personId")
	public List<Playlist> findPlaylistsByPersonId(@Param("personId") int personId);

}
