package edu.neu.cs5200.orm.jpa.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Person;
import edu.neu.cs5200.orm.jpa.entities.Playlist;
import edu.neu.cs5200.orm.jpa.entities.Track;

public interface PlaylistRepository extends CrudRepository<Track, Integer> {
	@Query("SELECT p FROM Playlist p WHERE p.name = :name")
	public List<Playlist> findPlaylistByName(@Param("name") String name);

	@Query("SELECT p FROM Playlist p WHERE p.createdBy = :personId")
	public List<Playlist> findPlaylistsByPersonId(@Param("personId") Person person);
}
