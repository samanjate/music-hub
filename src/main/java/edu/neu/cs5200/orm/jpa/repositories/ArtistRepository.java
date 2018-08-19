package edu.neu.cs5200.orm.jpa.repositories;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import edu.neu.cs5200.orm.jpa.entities.Artist;

public interface ArtistRepository extends CrudRepository<Artist, Integer> {


	@Query("SELECT t FROM Artist t WHERE t.napsterId = :napsterId")
	public Artist findArtistByNapsterId(@Param("napsterId") long id);
}
